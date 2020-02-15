/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//Spins the wheel that spins the color panel
package frc.robot.commands.controlpanel;

import frc.robot.Constants;

import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Map;
import java.util.HashMap;

public class SpinToPosition extends CommandBase {
    private String startColor;
    private int colorCount;
    private String previousColor;
    private String expectedColor;
    private String currentColor;
    private String[] expectedColorArray;
    private int arraySize;
    private int prevIndex;
    private Map<String,Integer> colorDictionary;
    private String color;

    public SpinToPosition() {
        addRequirements(RobotContainer.getContainer().getColorSensor());   
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        expectedColorArray = new String[]{"Yellow", "Red", "Green", "Blue"};
        arraySize = expectedColorArray.length;

        //Mapping colors to index numbers
        colorDictionary = new HashMap<String, Integer>();
        colorDictionary.put("Yellow", Integer.valueOf(0));
        colorDictionary.put("Red", Integer.valueOf(1));
        colorDictionary.put("Green", Integer.valueOf(2));
        colorDictionary.put("Blue", Integer.valueOf(3));

        color = RobotContainer.getContainer().getColorSensor().getColor();
        startColor = color;
        currentColor = color;
        colorCount = -1;
        // if(color == "Unknown") {
        //     SmartDashboard.putString("Unknown state", "true");
        //     end(true);
        // }
        
        // int prevIndex = (colorDictionary.get(startColor) - 1) >= 0 ? colorDictionary.get(startColor) - 1 : arraySize-1;
        // previousColor = expectedColorArray[prevIndex]; 
        // expectedColor = expectedColorArray[(colorDictionary.get(startColor) + 1) % arraySize];


        //{"Yellow", "Red", "Green", "Blue"}

        if(Constants.forward) {
        prevIndex = (colorDictionary.get(startColor) - 1) >= 0 ? colorDictionary.get(startColor) - 1 : arraySize-1;
        previousColor = expectedColorArray[prevIndex]; 
        expectedColor = expectedColorArray[(colorDictionary.get(startColor) + 1) % arraySize];
        }
        else {
        prevIndex = (colorDictionary.get(startColor) + 1) % arraySize;
        previousColor = expectedColorArray[prevIndex];
        expectedColor = expectedColorArray[(colorDictionary.get(startColor) - 1) >= 0 ? colorDictionary.get(startColor) - 1 : arraySize - 1];
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        System.out.println("exec");
        

        RobotContainer.getContainer().getColorPanelSpinner().spin(1); //change the speed

        //handling switch between yellow and blue
        currentColor = ((RobotContainer.getContainer().getColorSensor().getColor().equals("Green") && previousColor.equals("Blue")) ? "Blue" : RobotContainer.getContainer().getColorSensor().getColor());

        SmartDashboard.putString("currentColor", currentColor);
        SmartDashboard.putString("previousColor", previousColor);
        SmartDashboard.putNumber("colorCount", colorCount);
        SmartDashboard.putString("expectedColor", expectedColor);
        SmartDashboard.putString("startColor", startColor);
    
        if (currentColor.equals(startColor) && !currentColor.equals(previousColor)) {
            colorCount ++;
            
        }
        previousColor = currentColor;
        expectedColor =  expectedColorArray[(colorDictionary.get(currentColor) + 1) % 4];
        RobotContainer.getContainer().getColorPanelSpinner().printPosition();
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return colorCount > 7;
    }

    // Called once after isFinished returns true
    @Override
    public void end(final boolean interrupted) {
        RobotContainer.getContainer().getColorPanelSpinner().spin(0);
        colorCount = 0;
    }
}
