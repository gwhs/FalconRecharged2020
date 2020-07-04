/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoPaths.Auto2CycleTrenchRunAuto;
import frc.robot.commands.AutoPaths.AutoPath1;
import frc.robot.commands.AutoPaths.AutoPath2;
import frc.robot.commands.climber.*;
import frc.robot.commands.controlpanel.*;
import frc.robot.commands.conveyor.*;
import frc.robot.commands.intake.*;
import frc.robot.commands.shooter.AutoShoot;
import frc.robot.commands.shooter.SetShooterSpeed;
import frc.robot.commands.swervedrive.*;
import frc.robot.subsystems.ClimberTalon;
import frc.robot.subsystems.Color.ColorPanelSpinner;
import frc.robot.subsystems.Color.ColorSensor;
import frc.robot.subsystems.ConveyorTalon;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drive.SwerveDriveSubsystem;

//import sun.java2d.cmm.ColorTransform;
import frc.robot.utility.TrajectoryMaker;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Command m_autoCommand = null;
  private XboxController mXboxController;
  private XboxController mXboxController2;  //operator controller
  private static RobotContainer theContainer;
  private SwerveDriveSubsystem swerveDriveSubsystem;
  private ColorPanelSpinner colorPanelSpinner;
  private ColorSensor colorSensor;
  private Limelight limelight;
  private ConveyorTalon conveyorT;
  private Intake intake;
  private Shooter shooterMotor;
  private Compressor compressor;
  private ClimberTalon climberT;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    theContainer = this;
    // Configure the button bindings
    swerveDriveSubsystem = new SwerveDriveSubsystem();
    swerveDriveSubsystem.zeroGyro();
    colorPanelSpinner = new ColorPanelSpinner();
    colorSensor = new ColorSensor();
    mXboxController = new XboxController(0);
    mXboxController2 = new XboxController(1);
    limelight = new Limelight();
    conveyorT = new ConveyorTalon();
    intake = new Intake();
    shooterMotor = new Shooter();
    compressor = new Compressor();
    climberT = new ClimberTalon();
    configureButtonBindings();
    conveyorT.setDefaultCommand(new SenseCell());
    climberT.setDefaultCommand(new ClimberArmSpeed());
  }

  public Shooter getShooterMotor() {
    return shooterMotor;
  }

  public ColorPanelSpinner getColorPanelSpinner() {
    return colorPanelSpinner;
  }

  public ColorSensor getColorSensor() {
    return colorSensor;
  }

  public SwerveDriveSubsystem getHolonomicDrivetrain() {
    return swerveDriveSubsystem;
  }

  public XboxController getDriveController() {
    return mXboxController;
  }

  public XboxController getOperatorController() {
    return mXboxController2;
  }

  public static RobotContainer getContainer(){
    return theContainer;

  }

  public Limelight getLimelight() {
    return limelight;
  }

  public ConveyorTalon getConveyorT(){
    return conveyorT;
  }

  public Intake getIntake() {
    return intake;
  }

  public Compressor getCompressor() {
    return compressor;
  }
  public ClimberTalon getClimberT() {
    return climberT;
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

    buttonY.whileHeld(new ConveyorSpeed(.5));
    buttonB.whileHeld(new IntakeSpeed(.5));
    leftBumper.whileHeld(new ConveyorSpeed(-.7));
    rightBumper.whenPressed(new SetShooterSpeed());
    back.whileHeld(new ZeroNavX());
    //start.whenPressed(new AutoShoot());

    //start.whenPressed(new AutoRotate(45));

    start.whenPressed(new TurnToAngleProfiled(45, swerveDriveSubsystem));
    buttonX.whenPressed(new TurnToAngle(45, swerveDriveSubsystem));
    //start.whenPressed(new InstantCommand(swerveDriveSubsystem::holonomicDrive(0.0,0.0,45.0), swerveDriveSubsystem));
    //buttonX.whenPressed(new AutoPath1());
    //buttonA.whenPressed(new AutoPath2());
    //buttonB.whenPressed(new Auto2CycleTrenchRunAuto());
    //buttonY.whenPressed(new AutoRotate(173));
    

    // buttonA_2.whenPressed(new ToggleIgnore());
    // buttonX_2.whenPressed(new ToggleClimberGearLock(climberT));
    // buttonY_2.whenPressed(new SemiAutoClimb());
    // buttonB_2.whenPressed(new SemiAutoPullUp());
    
  }



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //How can we change this to select the auto routine from the dashboard?
    return new AutoPath1();

  }

  // Need better documentation here.  What are these doing?
  public TrajectoryMaker createfrontScorePath() 
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(3, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createTrenchToTargetDiagonal() 
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0.25, -0.25, new Rotation2d(0)), true);//8,-1.6
  }
  public TrajectoryMaker createTargetToFrontOfTrench() 
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-0.25, 0.25, new Rotation2d(0)), true);// -4.2,1.6
  }
  public TrajectoryMaker createTrenchForward() //Assuming facing forward
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0.25, 0, new Rotation2d(0)), true);//2
  }
  
  public TrajectoryMaker createForwardPath() //For Testing Purposes
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(1, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createForwardPath2() //For Testing Purposes
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
  }
  public TrajectoryMaker createToPortPath() //For Testing Purposes
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0.25, 0, new Rotation2d(0)), true);//3
  }

  public TrajectoryMaker createPortToFrontofTrench()
    {
      ArrayList<Translation2d> points = new ArrayList<Translation2d>();
      points.add(new Translation2d(-1.5, 2.3));
      points.add(new Translation2d(-3, 2.3));
      return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(-5.3, 2.3, new Rotation2d(180)), points );
    }
  public TrajectoryMaker createMoveDownTrench()
  {
    return new TrajectoryMaker(new Pose2d(0,0,new Rotation2d(0)), new Pose2d(3, 0, new Rotation2d(0)), true);
  }

  public TrajectoryMaker createMoveToPort()
  {
    ArrayList<Translation2d> points = new ArrayList<Translation2d>();
      points.add(new Translation2d(1.524, 2.286));
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(3.048, 4.572, new Rotation2d(0)), points );
  }
  

  public TrajectoryMaker createAutonomousPath1() // Init Line (Start on Left) to Port Test
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(2, 0, new Rotation2d(0)), true);
  }

  public TrajectoryMaker createAutonomousPath2() //Test 2 Electric Bugaloo
  {
    return new TrajectoryMaker(new Pose2d(0, 0, new Rotation2d(0)), new Pose2d(0, -1, new Rotation2d(0)), true);
  }
  
}