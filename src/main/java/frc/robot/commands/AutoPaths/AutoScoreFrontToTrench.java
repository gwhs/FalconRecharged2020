/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoPaths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.conveyor.ConveyorSpeed;
import frc.robot.commands.intake.IntakeSpeed;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.swervedrive.Autonomous;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoScoreFrontToTrench extends SequentialCommandGroup {
  /**
   * Creates a new AutoScoreFrontToTrench.
   * 
   * A test Auto for phase 1 robot. 
   * 
   * The Robot starts at the initiation line, dumps the 3 power cells inside the robot,
   * moves to the front of the trench, and picks up the 3 power cells at the trench.
   */
  public AutoScoreFrontToTrench() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new Autonomous(RobotContainer.getContainer().createfrontScorePath().getTrajectory(), RobotContainer.getContainer().createfrontScorePath().getAngle()), 
          new ConveyorSpeed(-1).withTimeout(1.5), 
          new Autonomous(RobotContainer.getContainer().createPortToFrontofTrench().getTrajectory(), 0.0),
          new ToggleIntake(),
          new IntakeSpeed(-0.8).raceWith(new Autonomous(RobotContainer.getContainer().createMoveDownTrench().getTrajectory(), RobotContainer.getContainer().createMoveDownTrench().getAngle()))





    );
  }
}
