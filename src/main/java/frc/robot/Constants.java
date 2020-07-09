/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import jdk.internal.jshell.tool.resources.l10n;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int CONVEYOR_SPARK = 8;
    public static final int CONVEYOR_TALON = 8; //change id
    public static final int INTAKE_SPARK = 9;
    public static final int CLIMBER1_SPARK = 10;
    public static final int CLIMBER2_SPARK = 11;
    public static final int CLIMBER1_TALON = 11; //change id
    public static final int CLIMBER2_TALON = 10; //change id
    public static final int ANGLE1_TALON = 1;
    public static final int ANGLE2_TALON = 3;
    public static final int ANGLE3_TALON = 7;
    public static final int ANGLE4_TALON = 5;
    public static final int DRIVE1_TALON = 0;
    public static final int DRIVE2_TALON = 2;
    public static final int DRIVE3_TALON = 6;
    public static final int DRIVE4_TALON = 4;
    public static final int INTAKEFORWARD_SOLENOID = 4;
    public static final int INTAKEREVERSE_SOLENOID = 5;
    public static final int INTAKEFORWARD_SOLENOID2 = 6;
    public static final int INTAKEREVERSE_SOLENOID2 = 7;
    public static final int CLIMBERFORWARD_SOLENOID = 2;
    public static final int CLIMBERREVERSE_SOLENOID = 3;
    public static final int COLORPANELFORWARD_SOLENOID = 0;
    public static final int COLORPANELREVERSE_SOLENOID = 1;
    public static final int SHOOTER1_TALON = 35;
    public static final int SHOOTER2_TALON = 36;
    public static final int SPINNER_SPARK = 51;

    public static final double GEAR_RATIO = 7.0;
    public static final double WHEEL_SIZE = 3.838;

    public static final double TICKS_PER_INCH = (2048*GEAR_RATIO)/(WHEEL_SIZE*Math.PI);
    public static boolean forward = true;  

    public static final double MOD_TO_CENTER = 0.28575; // distance in meter from center point to each swerve module


    //to do: Make these final
    public static double maxAngleVelocity = 180;
    public static double maxAngleAcceleration = 360;

    public static double turnTolerance = 0.5;
    public static double anglePIDp = 0.0361;
    public static double anglePIDi = 0.00;
    public static double anglePIDd = 0.00237;
}
