/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterMotor;


public class SetShooterSpeed extends CommandBase {
  /**
   * Creates a new SetShooterSpeed.
   */
  private double speed; 
  private ShooterMotor shooterMotor;
  private double pidF;
  private double pidP;
  private double pidI;
  private double pidD;


  public SetShooterSpeed() {    
    
    SmartDashboard.putNumber("Current Shooter RPM", 0);
    SmartDashboard.putNumber("Input Shooter RPM", SmartDashboard.getNumber("Input Shooter RPM", 3750));
    SmartDashboard.putNumber("Input pidF", SmartDashboard.getNumber("Input pidF", 0.04928));
    SmartDashboard.putNumber("Input pidP", SmartDashboard.getNumber("Input pidP", 0.1));
    SmartDashboard.putNumber("Input pidI", SmartDashboard.getNumber("Input pidI", 0));
    SmartDashboard.putNumber("Input pidD", SmartDashboard.getNumber("Input pidD", 2.5));
    shooterMotor = RobotContainer.getContainer().getShooterMotor();
    addRequirements(shooterMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    shooterMotor.setSpeed(-speed);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //shooterMotor.setSpeed(mXboxController.getY(Hand.kRight));
    // if(RobotContainer.getContainer().getOperatorController().getXButtonPressed())
    // {
      this.speed = SmartDashboard.getNumber("Input Shooter RPM", 3750);
      shooterMotor.setSpeed(-speed);
      this.pidF = SmartDashboard.getNumber("Input pidF", 0);
      this.pidP = SmartDashboard.getNumber("Input pidP", 0);
      this.pidI = SmartDashboard.getNumber("Input pidI", 0);
      this.pidD = SmartDashboard.getNumber("Input pidD", 0);
      shooterMotor.getmotor1().config_kF(0, this.pidF, 0);
      shooterMotor.getmotor2().config_kF(0, this.pidF, 0);

      shooterMotor.getmotor1().config_kP(0, this.pidP, 0);
      shooterMotor.getmotor2().config_kP(0, this.pidP, 0);

      shooterMotor.getmotor1().config_kI(0, this.pidI, 0);
      shooterMotor.getmotor2().config_kI(0, this.pidI, 0);

      shooterMotor.getmotor1().config_kD(0, this.pidD, 0);
      shooterMotor.getmotor2().config_kD(0, this.pidD, 0);

    //}
    SmartDashboard.putNumber("Current Shooter RPM", -shooterMotor.getSpeed());
    SmartDashboard.putNumber("pidF", this.pidF);
    SmartDashboard.putNumber("pidP", this.pidP);
    SmartDashboard.putNumber("pidI", this.pidI);
    SmartDashboard.putNumber("pidD", this.pidD);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooterMotor.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
