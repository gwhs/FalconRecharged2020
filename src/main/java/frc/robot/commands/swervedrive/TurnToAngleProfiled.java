package frc.robot.commands.swervedrive;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.subsystems.Drive.*;
import frc.robot.Constants;


/**
 * A command that will turn the robot to the specified angle.
 */
public class TurnToAngleProfiled extends ProfiledPIDCommand {
    SwerveDriveSubsystem swerveDriveSubsystem;
     TrapezoidProfile.Constraints rampUpDown = new TrapezoidProfile.Constraints(10,5);

    /**
     * Turns to robot to the specified angle.
     *
     * @param targetAngleDegrees The angle to turn to
     * @param drive              The drive subsystem to use
     */
    public TurnToAngleProfiled(double targetAngleDegrees, SwerveDriveSubsystem drive) {
    super(
        new ProfiledPIDController(Constants.anglePIDp,Constants.anglePIDi, Constants.anglePIDd, //need to tune this better
            new TrapezoidProfile.Constraints(Constants.maxAngleVelocity,Constants.maxAngleAcceleration)),  
        
        // Close loop on heading
        drive::getGyroAngle2,
        // Set reference to target
        targetAngleDegrees,  
        // Pipe output to turn branchrobot
        (output,setpoint) -> drive.holonomicDrive(0, 0, output),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    swerveDriveSubsystem = drive;
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController().setTolerance(Constants.turnTolerance, 10);
  }


  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
    System.out.println("angle: " 
        + swerveDriveSubsystem.getGyroAngle2());
  }
  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atGoal();
  }


}