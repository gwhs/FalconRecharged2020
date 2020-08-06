/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.ReadLimelight;

import frc.robot.commands.AutoPaths.AutoPath1;
import frc.robot.commands.climber.*;
//import frc.robot.commands.controlpanel.SpinnerCommand;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.commands.shooter.SetShooterSpeed;
import frc.robot.commands.swervedrive.*;
import frc.robot.subsystems.ClimberTalon;
import frc.robot.subsystems.Color.ColorPanelSpinner;
import frc.robot.subsystems.Color.ColorSensor;
import frc.robot.subsystems.ConveyorTalon;
import frc.robot.subsystems.Drive.SwerveDriveModule;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.LimelightPortal;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class DaphneTwoContainer {
  // The robot's subsystems and commands are defined here...

  private final XboxController mXboxController;
  private final XboxController mXboxController2;  //operator controller

  private final SwerveDriveSubsystem swerveDriveSubsystem;
  private final ColorPanelSpinner colorPanelSpinner;
  private final ColorSensor colorSensor;
  private final Limelight limelight;
  private final ConveyorTalon conveyorT;
  private final Intake intake;
  private final Shooter shooterMotor;
  private final Compressor compressor;
  private final ClimberTalon climberT;
  private final LimelightPortal limeL;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public DaphneTwoContainer() {
    // create all the subsystems needed in this robot
    SwerveDriveModule m0 = new SwerveDriveModule(0, new TalonSRX(DaphneTwoConstants.ANGLE2_TALON), new TalonFX(DaphneTwoConstants.DRIVE2_TALON), 169); //real:390 practice: 212
    SwerveDriveModule m1 = new SwerveDriveModule(1, new TalonSRX(DaphneTwoConstants.ANGLE1_TALON), new TalonFX(DaphneTwoConstants.DRIVE1_TALON), 176); //real:293 practice: 59
    SwerveDriveModule m2 = new SwerveDriveModule(2, new TalonSRX(DaphneTwoConstants.ANGLE3_TALON), new TalonFX(DaphneTwoConstants.DRIVE3_TALON), 294); //real:298 practice: 56
    SwerveDriveModule m3 = new SwerveDriveModule(3, new TalonSRX(DaphneTwoConstants.ANGLE4_TALON), new TalonFX(DaphneTwoConstants.DRIVE4_TALON), 27); //real: 355 practice: 190

    swerveDriveSubsystem = new SwerveDriveSubsystem(m0, m1, m2, m3);
    swerveDriveSubsystem.zeroGyro();
    colorPanelSpinner = new ColorPanelSpinner();
    colorSensor = new ColorSensor();
    limelight = new Limelight(swerveDriveSubsystem);
    conveyorT = new ConveyorTalon();
    intake = new Intake();
    shooterMotor = new Shooter();
    compressor = new Compressor();
    climberT = new ClimberTalon();
    limeL = new LimelightPortal();

    // create the input controllers
    mXboxController = new XboxController(0);
    mXboxController2 = new XboxController(1);

    // setup any default commands
    swerveDriveSubsystem.setDefaultCommand(new HolonomicDriveCommand(swerveDriveSubsystem, mXboxController));
    //colorPanelSpinner.setDefaultCommand(new SpinnerCommand(colorPanelSpinner, mXboxController2));
    conveyorT.setDefaultCommand(new SenseCell(conveyorT));
    climberT.setDefaultCommand(new ClimberArmSpeed(climberT, mXboxController2));

    // configure the buttons
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton buttonA = new JoystickButton(mXboxController, XboxController.Button.kA.value);
    JoystickButton buttonX = new JoystickButton(mXboxController, XboxController.Button.kX.value);
    JoystickButton buttonB = new JoystickButton(mXboxController, XboxController.Button.kB.value);
    JoystickButton buttonY = new JoystickButton(mXboxController, XboxController.Button.kY.value);
    JoystickButton leftBumper = new JoystickButton(mXboxController, XboxController.Button.kBumperLeft.value);
    JoystickButton rightBumper = new JoystickButton(mXboxController, XboxController.Button.kBumperRight.value);
    JoystickButton back = new JoystickButton(mXboxController, XboxController.Button.kBack.value);
    JoystickButton start = new JoystickButton(mXboxController, XboxController.Button.kStart.value);


    JoystickButton buttonA_2 = new JoystickButton(mXboxController2, XboxController.Button.kA.value);
    JoystickButton buttonX_2 = new JoystickButton(mXboxController2, XboxController.Button.kX.value);
    JoystickButton buttonB_2 = new JoystickButton(mXboxController2, XboxController.Button.kB.value);
    JoystickButton buttonY_2 = new JoystickButton(mXboxController2, XboxController.Button.kY.value);
    JoystickButton leftBumper_2 = new JoystickButton(mXboxController2, XboxController.Button.kBumperLeft.value);
    JoystickButton rightBumper_2 = new JoystickButton(mXboxController2, XboxController.Button.kBumperRight.value);
    
    
    //buttonX.whileHeld(new IntakeSpeed(-0.8));
    //buttonA.whenPressed(new ToggleIntake());  //testing inline
    /*new JoystickButton(m_driverController, Button.kB.value)
        .whenPressed(new InstantCommand(m_hatchSubsystem::releaseHatch, m_hatchSubsystem));*/
    /*
    The following is an example of an inline command.  No need to create a CommandBase Subclass for simple commands
    */
    buttonA.whenPressed(new InstantCommand(intake::toggleIntakeSolenoidMode, intake));

    buttonY.whileHeld(new ConveyorSpeed( conveyorT, .5));
    buttonB.whileHeld(new IntakeSpeed(intake,.5));
    leftBumper.whileHeld(new ConveyorSpeed( conveyorT, -.7));
    rightBumper.whenPressed(new SetShooterSpeed(shooterMotor));
    back.whileHeld(new ZeroNavX(swerveDriveSubsystem));
    //start.whenPressed(new AutoShoot(conveyorT, shooterMotor,false));
    //start.whileHeld(new ReadLimelight(limeL));
    //start.whenPressed(new RotateWithLimelight(limeL, swerveDriveSubsystem));
    //start.whenPressed(new TurnToZeroLimelight(0, swerveDriveSubsystem, limeL));
    //start.whenPressed(new TurnToAngleProfiled(12, swerveDriveSubsystem));
    start.whenPressed(new AlignToTargetLimelight( swerveDriveSubsystem, limeL));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //How can we change this to select the auto routine from the dashboard?
    return new AutoPath1(swerveDriveSubsystem);
  }
}