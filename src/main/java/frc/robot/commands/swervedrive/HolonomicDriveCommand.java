/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.swervedrive;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drive.HolonomicDrivetrain;
import frc.robot.utility.MathUtils;

public class HolonomicDriveCommand extends CommandBase {
  /**
   * Creates a new HolonomicDriveCommand.
   */
  private final HolonomicDrivetrain mDrivetrain;
  private XboxController mXboxController;

	public HolonomicDriveCommand(HolonomicDrivetrain drivetrain) {
		mDrivetrain = drivetrain;

		addRequirements(drivetrain);
		
	}

	@Override
	public void execute() {
		mXboxController = RobotContainer.getContainer().getDriveController();
		if(RobotContainer.getContainer().getHolonomicDrivetrain().getIsAuto())
		{
			mDrivetrain.setFieldOriented(false);
		}
		
		double forward = mXboxController.getY(Hand.kLeft); //real: positive
		double rotation = -(mXboxController.getTriggerAxis(Hand.kRight) - mXboxController.getTriggerAxis(Hand.kLeft)); //trigger values are between 0 and 1, left is -1 and right is +1
		double strafe = -mXboxController.getX(Hand.kLeft); //real: pos

		forward = MathUtils.deadband(forward, mDrivetrain.isFieldOriented());
		strafe = MathUtils.deadband(strafe, mDrivetrain.isFieldOriented());
		rotation = MathUtils.deadband(rotation, mDrivetrain.isFieldOriented());

		
		mDrivetrain.swapPIDSlot(0);
		mDrivetrain.holonomicDrive(forward, strafe, rotation);
	}

	@Override
	public void end(boolean interrupted) {
		mDrivetrain.stopDriveMotors();
	}
}
