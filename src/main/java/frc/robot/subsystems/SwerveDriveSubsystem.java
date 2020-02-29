package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;


import edu.wpi.first.wpilibj.SPI;
import frc.robot.Constants;
import frc.robot.commands.HolonomicDriveCommand;

public class SwerveDriveSubsystem extends HolonomicDrivetrain { // - is clockwise + is counter clockwise test commit 2 electric bugaloo
	private static final double WHEELBASE = 22.5; 
	private static final double TRACKWIDTH = 22.5;
	private static final double RATIO = Math.sqrt(Math.pow(WHEELBASE, 2) + Math.pow(TRACKWIDTH, 2));
	public SwerveDriveModule m0 = new SwerveDriveModule(0, new TalonSRX(Constants.ANGLE2_TALON), new TalonFX(Constants.DRIVE2_TALON), 165); //real:390 practice: 212
	public SwerveDriveModule m1 = new SwerveDriveModule(1, new TalonSRX(Constants.ANGLE1_TALON), new TalonFX(Constants.DRIVE1_TALON), 172); //real:293 practice: 59
	public SwerveDriveModule m2 = new SwerveDriveModule(2, new TalonSRX(Constants.ANGLE3_TALON), new TalonFX(Constants.DRIVE3_TALON), 290); //real:298 practice: 56
	public SwerveDriveModule m3 = new SwerveDriveModule(3, new TalonSRX(Constants.ANGLE4_TALON), new TalonFX(Constants.DRIVE4_TALON), 30); //real: 355 practice: 190

	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
	private SwerveDriveModule[] mSwerveModules = new SwerveDriveModule[] {                            
		m0,m1,m2,m3
	};

	public AHRS mNavX = new AHRS(SPI.Port.kMXP, (byte) 200);

	public SwerveDriveSubsystem() { // add PID controll stuff for Drive Motors
		zeroGyro(); 

		mSwerveModules[0].getDriveMotor().setInverted(false); //real: false
		mSwerveModules[1].getDriveMotor().setInverted(true); //real: true
		mSwerveModules[2].getDriveMotor().setInverted(false); //real: false
		mSwerveModules[3].getDriveMotor().setInverted(false); //real: false

		mSwerveModules[0].getAngleMotor().setInverted(true); //real: true
		mSwerveModules[2].getAngleMotor().setInverted(true); //real: true
		mSwerveModules[1].getAngleMotor().setInverted(true); //real: true
		mSwerveModules[3].getAngleMotor().setInverted(true); //real: true

		this.resetAllEncoders();

		//mSwerveModules[0].resetEncoder();
		for(int i = 0; i < 4; i++) {
			mSwerveModules[i].getDriveMotor().setNeutralMode(NeutralMode.Brake);
		}

		setDefaultCommand(new HolonomicDriveCommand(this));
	}

	public AHRS getNavX() {
		return mNavX;
	}
	//changeeeeeeeeeeeee for new robot 90 degrees
	public double getGyroAngle() {
		return (mNavX.getAngle() - getAdjustmentAngle());
	}

	public double getGyroRate() {
		return mNavX.getRate();
	}

	public double getRawGyroAngle() {
		return mNavX.getAngle();
	}

	public double getYaw()
	{
		return mNavX.getAngle();
	}

	public SwerveDriveModule getSwerveModule(int i) {
		return mSwerveModules[i];
	}

	@Override
	public void holonomicDrive(double forward, double strafe, double rotation) {
		forward *= getSpeedMultiplier();
		strafe *= getSpeedMultiplier();
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

		double max = speeds[0];

		for (double speed : speeds) { 
			if (speed > max) {
				max = speed;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (Math.abs(forward) > 0.05 ||
			    Math.abs(strafe) > 0.05 ||
			    Math.abs(rotation) > 0.05) {
				mSwerveModules[i].setTargetAngle(angles[i] + 180);
			} else {
				mSwerveModules[i].setTargetAngle(mSwerveModules[i].getTargetAngle());
			}
			mSwerveModules[i].setTargetSpeed(speeds[i]);
		}
	}

	@Override
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

	public void driveForwardDistance(double targetPos, double angle){ // inches & degrees
		double angleError = ((angle - mNavX.getYaw()) / 180)*10;

		angleError = Math.min(angleError, 1);
		angleError = Math.max(angleError, -1);
		targetPos = (targetPos * 8.5714)/(4*Math.PI); //inches to ticks
		for (int i = 0; i < 4; i++) {
				mSwerveModules[i].setTargetAngle(angle); //mSwerveModules[i].getTargetAngle());
				mSwerveModules[i].setTargetDistance(targetPos+mSwerveModules[i].getDriveMotor().getSelectedSensorPosition());
			}
			
	} // 2/12/19 3:37 PM i want boba and a burrito so bad right now !!!!!!!!!

	public void driveSidewaysDistance(double targetPos, double angle, double speed) {
		double angleError = ((angle - mNavX.getYaw()) / 180)*10;
		angleError = Math.min(angleError, 1);
		angleError = Math.max(angleError, -1);
		//holonomicDrive(0, speed, angleError);
	}

	public double calculateErrPos(double d1) {
		return d1 - mSwerveModules[0].getInches(); //return d1 - mSwerveModules[0].getDriveDistance();
	}

}

