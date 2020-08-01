package frc.robot.subsystems.Drive;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class HolonomicDrivetrain extends SubsystemBase {


	private double speedMultiplier = 1;
	private double mAdjustmentAngle = 0;
	private boolean mFieldOriented = true;

	public abstract double getGyroAngle();

	public abstract void holonomicDrive(double forward, double strafe, double rotation);

	public abstract void swapPIDSlot(int slot);

	public double getAdjustmentAngle() {
		return mAdjustmentAngle;
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
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

	public abstract void stopDriveMotors();

	public void zeroGyro() {
		setAdjustmentAngle(getGyroAngle() + getAdjustmentAngle());
	}
}
