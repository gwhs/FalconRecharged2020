/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ToggleIntake extends InstantCommand {
  /**
   * 
   * Used to toggle the intake arm. 
   * Calling this command changes between the intake arm's up position, and its down position(when it can intake).
   * 
   */
  public ToggleIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getIntake());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.getContainer().getIntake().toggleIntakeSolenoidMode();
  }
}
