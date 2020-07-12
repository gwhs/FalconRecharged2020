/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Color.ColorPanelSpinner;
import frc.robot.subsystems.Color.ColorSensor;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SpinToMidColor extends SequentialCommandGroup {
  /**
   * Creates a new SpinToMidColor.
   */
  public SpinToMidColor(ColorSensor colorSensor, ColorPanelSpinner colorPanelSpinner) { //viv bday = 3/6
    // Add your commands in the super() call, e.g.
    super(new SpinToColor(colorSensor, colorPanelSpinner), new SpinToMid(colorSensor, colorPanelSpinner));
  }
}
