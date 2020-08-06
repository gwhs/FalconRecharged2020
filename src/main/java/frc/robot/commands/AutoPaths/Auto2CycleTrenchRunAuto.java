/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoPaths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.TrajectoryHelper;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.commands.swervedrive.AutoRotate;
import frc.robot.commands.swervedrive.Autonomous;
import frc.robot.subsystems.ConveyorTalon;
import frc.robot.subsystems.drive.SwerveDriveSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.TrajectoryMaker;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Auto2CycleTrenchRunAuto extends SequentialCommandGroup {
  /**
   * Creates a new Auto2CycleTrenchRunAuto.
   */
  public Auto2CycleTrenchRunAuto(SwerveDriveSubsystem swerveDriveSubsystem, ConveyorTalon conveyorTalon, Intake intake, Shooter shooter) {
    // Start at Initiation Line
    // This is all theoretical code with no actual field measurements. 
    // MAKE SURE TO MEASURE AND SWAP VALUES BEFORE TESTING - Kyle

    TrajectoryMaker traj = TrajectoryHelper.createToPortPath();
    TrajectoryMaker trajTrench = TrajectoryHelper.createTargetToFrontOfTrench();
    TrajectoryMaker trajTrenchForward = TrajectoryHelper.createTrenchForward();
    TrajectoryMaker trajTrenchTarget = TrajectoryHelper.createTrenchToTargetDiagonal();
    addCommands(
      new Autonomous(swerveDriveSubsystem, traj.getTrajectory(), traj.getAngle()),
      new AutoShoot(conveyorTalon, shooter,false),
      new Autonomous(swerveDriveSubsystem, trajTrench.getTrajectory(), trajTrench.getAngle()),
      new AutoRotate(swerveDriveSubsystem,175),
      new Autonomous(swerveDriveSubsystem, trajTrenchForward.getTrajectory(), trajTrenchForward.getAngle()).raceWith(new IntakeSpeed(intake, -1)),
      new AutoRotate(swerveDriveSubsystem,175),
      new Autonomous(swerveDriveSubsystem, trajTrenchTarget.getTrajectory(), trajTrenchTarget.getAngle()),
      new AutoShoot(conveyorTalon,shooter,true)
    );
  }
}
