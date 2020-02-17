/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controlpanel;

import frc.robot.Constants;
import frc.robot.Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class SpinToMid extends CommandBase {
  /**
   * Creates a new SenseColorTest.
   */

  private String currentColor;
  private String previousColor;
  private String expectedColor;
  private String prevColor;
  private String[] expectedColorArray;
  private int arraySize;
  private int prevIndex;
  private Map<String, Integer> colorDictionary;
  private double segmentLength;
  private double targetPos;
  private String gameData;
  private ArrayList<ColorPosition> colorPosition = new ArrayList<ColorPosition>();
  private boolean goingForward2;

  public SpinToMid(String data) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getContainer().getColorSensor());
    if (data == null) {
      gameData = "Unknown";
    } else if (data.length() == 0) {
      gameData = "Unknown";
    } else if (data.charAt(0) == 'G') {
      gameData = "Yellow";
    } else if (data.charAt(0) == 'B') {
      gameData = "Red";
    } else if (data.charAt(0) == 'Y') {
      gameData = "Green";
    } else if (data.charAt(0) == 'R') {
      gameData = "Blue";
    } else {
      gameData = "Unknown";
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    String startColor;
    // RobotContainer.getContainer().getColorPanelSpinner().resetEncoder();
    expectedColorArray = new String[] { "Yellow", "Red", "Green", "Blue" };
    arraySize = expectedColorArray.length;

    // Mapping colors to index numbers
    colorDictionary = new HashMap<String, Integer>();
    colorDictionary.put("Yellow", Integer.valueOf(0));
    colorDictionary.put("Red", Integer.valueOf(1));
    colorDictionary.put("Green", Integer.valueOf(2));
    colorDictionary.put("Blue", Integer.valueOf(3));

    currentColor = RobotContainer.getContainer().getColorSensor().getColor();
    startColor = currentColor;

    if(Constants.forward) {
    prevIndex = (colorDictionary.get(startColor) - 1) >= 0 ?
    colorDictionary.get(startColor) - 1 : arraySize-1;
    previousColor = expectedColorArray[prevIndex];
    expectedColor = expectedColorArray[(colorDictionary.get(startColor) + 1) %
    arraySize];
    prevColor = previousColor;
    }
    else {
    prevIndex = (colorDictionary.get(startColor) + 1) % arraySize;
    previousColor = expectedColorArray[prevIndex];
    expectedColor = expectedColorArray[(colorDictionary.get(startColor) - 1) >= 0
    ? colorDictionary.get(startColor) - 1 : arraySize - 1];
    prevColor = previousColor;
    }

    SmartDashboard.putString("previousColor", prevColor);
     SmartDashboard.putString("expColor", expectedColor);
    findMid();
    // RobotContainer.getContainer().getColorPanelSpinner().resetEncoder();
  }

  public void updateColor() {
    prevColor = currentColor;    
    //boolean goingForward = RobotContainer.getContainer().getColorPanelSpinner().getMotorSpeed() > 0.0;
    System.out.println(RobotContainer.getContainer().getColorPanelSpinner().getMotorSpeed());
   // double position = RobotContainer.getContainer().getColorPanelSpinner().getPosition();
    //colorPosition.add(new ColorPosition(currentColor, position));

    if (gameData.equals("Red")) {
      if (goingForward2) {
        if (RobotContainer.getContainer().getColorSensor().getColor().equals("Yellow")) {
          currentColor = "Green";
        }
      }
      else if (!goingForward2 && RobotContainer.getContainer().getColorSensor().getColor().equals("Yellow") && prevColor.equals("Green")){ 
        currentColor = "Red";
      }
    }
    else{
      currentColor = RobotContainer.getContainer().getColorSensor().getColor();
    }

  }

  

  public void findMid() {
    SmartDashboard.putNumber("init pos", RobotContainer.getContainer().getColorPanelSpinner().getPosition());
    updateColor();
    
    SmartDashboard.putString("currentColor", currentColor);
    while (currentColor != expectedColor) {
      SmartDashboard.putString("currentColor", currentColor);
      goingForward2 = true;
      RobotContainer.getContainer().getColorPanelSpinner().spin(.15);
      updateColor();
      RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    }
    RobotContainer.getContainer().getColorPanelSpinner().spin(0);
    double forwardPos = RobotContainer.getContainer().getColorPanelSpinner().getPosition();
    RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    SmartDashboard.putNumber("forward pos", forwardPos);

    while (currentColor != prevColor) {
      SmartDashboard.putString("currentColor", currentColor);
      goingForward2 = false;
      RobotContainer.getContainer().getColorPanelSpinner().spin(-.15);

      updateColor();
      goingForward2 = true;
      RobotContainer.getContainer().getColorPanelSpinner().spin(0);
    }

    double finalPos = RobotContainer.getContainer().getColorPanelSpinner().getPosition();
    RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    segmentLength = Math.abs(forwardPos - finalPos);
    SmartDashboard.putNumber("backward pos", finalPos);

    double midPos = segmentLength / 2;
    targetPos = finalPos + midPos;

    SmartDashboard.putNumber("Target pos", targetPos);
    RobotContainer.getContainer().getColorPanelSpinner().setPosition(targetPos);
    RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    SmartDashboard.putString("currentColor", currentColor);
    SmartDashboard.putNumber("Segment length", segmentLength);
    SmartDashboard.putString("prevColor", prevColor);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(RobotContainer.getContainer().getColorPanelSpinner().getPosition() - targetPos) < 0.01;
  }

  @Override
  public void end(final boolean interrupted) {
    RobotContainer.getContainer().getColorPanelSpinner().spin(0);
  }
}
