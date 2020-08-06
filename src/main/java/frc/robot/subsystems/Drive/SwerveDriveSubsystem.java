package frc.robot.subsystems.Drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveDriveSubsystem extends SubsystemBase { // + is clockwise - is counter clockwise 
	private static final double WHEELBASE = 22.5; 
	private static final double TRACKWIDTH = 22.5;
	private static final double RATIO = Math.sqrt(Math.pow(WHEELBASE, 2) + Math.pow(TRACKWIDTH, 2));

	private double speedMultiplier = 1; // used to directly control proportional speed control
	private double mAdjustmentAngle = 0;
	private boolean mFieldOriented = true;
	
	/*
	 * while true, robot will be controlled based on the orientation of the field regardless of robot orientation 
	 * (forward on joystick will always move robot forward on the field) 
	 * while false, robot will be controlled based on the orientation of the robot
	 * (forward on joystick will move robot in the direction that it is currently facing)
	 */

	private boolean isAuto;
	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
	private final SwerveDriveModule[] mSwerveModules = new SwerveDriveModule[4];

	public AHRS mNavX = new AHRS(SPI.Port.kMXP, (byte) 200);

	public SwerveDriveSubsystem(SwerveDriveModule m0, SwerveDriveModule m1, SwerveDriveModule m2, SwerveDriveModule m3) { 
		initModules(m0, m1, m2, m3);
	}

	private void initModules(SwerveDriveModule m0, SwerveDriveModule m1, SwerveDriveModule m2, SwerveDriveModule m3) { 
		mSwerveModules[0] = m0;
		mSwerveModules[1] = m1;
		mSwerveModules[2] = m2;
		mSwerveModules[3] = m3;
		zeroGyro();

		 mSwerveModules[0].getAngleMotor().setInverted(true); //real: true
		 mSwerveModules[2].getAngleMotor().setInverted(true); //real: true
		 mSwerveModules[1].getAngleMotor().setInverted(true); //real: true
		 mSwerveModules[3].getAngleMotor().setInverted(true); //real: true

		mSwerveModules[0].resetEncoder();
		for(int i = 0; i < 4; i++) {
			mSwerveModules[i].getDriveMotor().setNeutralMode(NeutralMode.Brake);
		}

		isAuto = false;
	}

	public AHRS getNavX() {
		return mNavX;
	}

	public double getAdjustmentAngle() {
		return mAdjustmentAngle;
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	public boolean isFieldOriented() {
		return mFieldOriented;
	}	

	public void setAdjustmentAngle(double adjustmentAngle) {
		mAdjustmentAngle = adjustmentAngle;
	}

	public void setFieldOriented(boolean fieldOriented) {
		mFieldOriented = fieldOriented;
	}

	public double getGyroAngle() {
		return (mNavX.getAngle() - getAdjustmentAngle());
	}

	public double getGyroAngle2() {
		return (-getGyroAngle());
	}

	public double getGyroRate() {
		return mNavX.getRate();
	}

	public double getYaw()
	{
		return mNavX.getAngle();
	}

	public double getPitch()
	{
		return mNavX.getPitch();
	}

	public double getRoll()
	{
		return  mNavX.getRoll();
	}

	public SwerveDriveModule getSwerveModule(int i) {
		return mSwerveModules[i];
	}
	//Main method for driving the robot where each parameter moves the robot in its respected direction using inverse kinematics (complicated math that we dont understand).
	// 

	public void holonomicDrive(double forward, double strafe, double rotation) {  
		forward *= speedMultiplier;
		strafe *= speedMultiplier;
		if (isFieldOriented()) {
			
			double angleRad = Math.toRadians(getGyroAngle());
			double temp = forward * Math.cos(angleRad) +
					strafe * Math.sin(angleRad);
			strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
			forward = temp;
		}
		
		double a = strafe - rotation * (WHEELBASE / TRACKWIDTH);
		double b = strafe + rotation * (WHEELBASE / TRACKWIDTH);
		double c = forward - rotation * (TRACKWIDTH / WHEELBASE);
		double d = forward + rotation * (TRACKWIDTH / WHEELBASE);

		double[] angles = new double[]{
				Math.atan2(b, c) * 180 / Math.PI,
				Math.atan2(b, d) * 180 / Math.PI,
				Math.atan2(a, d) * 180 / Math.PI,
				Math.atan2(a, c) * 180 / Math.PI
		};

		double[] speeds = new double[]{
				Math.sqrt(b * b + c * c),
				Math.sqrt(b * b + d * d),
				Math.sqrt(a * a + d * d),
				Math.sqrt(a * a + c * c)
		};

		SmartDashboard.putNumber("Module 0 Ticks", mSwerveModules[0].getPosition());
		SmartDashboard.putNumber("Module 1 Ticks", mSwerveModules[1].getPosition());
		SmartDashboard.putNumber("Module 2 Ticks", mSwerveModules[2].getPosition());
		SmartDashboard.putNumber("Module 3 Ticks", mSwerveModules[3].getPosition());

		double max = speeds[0];  //remove?

		for (double speed : speeds) {  //regular for loop is preferred here, do we use max anywhere?  -- JMH
			if (speed > max) {
				max = speed;
			}
		}

		for (int i = 0; i < 4; i++) {  
			if (Math.abs(forward) > 0.05 ||
			    Math.abs(strafe) > 0.05 ||
			    Math.abs(rotation) > 0.05) {
				mSwerveModules[i].setTargetAngle(angles[i] + 180, isAuto);
			} else {
				mSwerveModules[i].setTargetAngle(mSwerveModules[i].getTargetAngle(), isAuto);
			}
			mSwerveModules[i].setTargetSpeed(speeds[i]);
		}
	}

	public void stopDriveMotors() {
		for (SwerveDriveModule module : mSwerveModules) {
			module.setTargetSpeed(0);
		}
	}

	public void resetAllEncoders() {
		for(int i = 0; i < 4; i++) {
			mSwerveModules[i].resetEncoder();
		}
	}

	public double getInches()
	{
		return mSwerveModules[0].getInches();
	}

	public void driveForwardDistance(double targetPos, double angle){ // inches & degrees
		double angleError = ((angle - mNavX.getYaw()) / 180)*10;

		angleError = Math.min(angleError, 1);
		angleError = Math.max(angleError, -1);
		targetPos = (targetPos * Constants.GEAR_RATIO)/(Constants.WHEEL_SIZE*Math.PI); //inches to ticks  -- can remove this magic number?  
		for (int i = 0; i < 4; i++) {
				mSwerveModules[i].setTargetAngle(angle, isAuto); //mSwerveModules[i].getTargetAngle());
				mSwerveModules[i].setTargetDistance(targetPos+mSwerveModules[i].getDriveMotor().getSelectedSensorPosition());
			}
			
	} // 2/12/19 3:37 PM i want boba and a burrito so bad right now !!!!!!!!!

	public void swapPIDSlot(int slot)
	{
		for(int i = 0; i < 4; i++)
		{
			mSwerveModules[i].setPIDSlot(slot);
		}
	}

	public void swapDrivePIDSlot(int slot)
	{
		for(int i = 0; i < 4; i++)
		{
			mSwerveModules[i].setDrivePIDSlot(slot);
		}
	}

	public void driveSidewaysDistance(double targetPos, double angle, double speed) {
		double angleError = ((angle - mNavX.getYaw()) / 180)*10;
		angleError = Math.min(angleError, 1);
		angleError = Math.max(angleError, -1);
		//holonomicDrive(0, speed, angleError);
	}

	public double calculateErrPos(double d1) {
		return d1 - mSwerveModules[0].getInches(); //return d1 - mSwerveModules[0].getDriveDistance();
	}

	public boolean getIsAuto()
	{
		return isAuto;
	}

	public void setIsAuto(boolean is)
	{
		isAuto = is;
	}

	public void zeroGyro() {
		setAdjustmentAngle(getGyroAngle() + getAdjustmentAngle());
	}

	@Override 
	public void periodic() { 
		SmartDashboard.putBoolean("Mod 0 Motor Inversion", mSwerveModules[0].getDriveMotor().getInverted());
		SmartDashboard.putBoolean("Mod 1 Motor Inversion", mSwerveModules[1].getDriveMotor().getInverted());
		SmartDashboard.putBoolean("Mod 2 Motor Inversion", mSwerveModules[2].getDriveMotor().getInverted());
		SmartDashboard.putBoolean("Mod 3 Motor Inversion", mSwerveModules[3].getDriveMotor().getInverted());
		SmartDashboard.putNumber("Mod 0 Angle", mSwerveModules[0].getCurrentAngle());
		SmartDashboard.putNumber("Mod 1 Angle", mSwerveModules[1].getCurrentAngle());
		SmartDashboard.putNumber("Mod 2 Angle", mSwerveModules[2].getCurrentAngle());
		SmartDashboard.putNumber("Mod 3 Angle", mSwerveModules[3].getCurrentAngle());
	}
}

