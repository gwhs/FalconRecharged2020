/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.conveyor.ConveyorSpeed;
import frc.robot.commands.conveyor.ToggleIgnore;
import frc.robot.subsystems.ConveyorTalon;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoShoot extends SequentialCommandGroup {
  /**
   * Creates a new AutoShoot.
   */
  public AutoShoot(ConveyorTalon conveyorTalon, Shooter shooter, boolean backConveyor) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ToggleIgnore(conveyorTalon, true),
          backConveyor ? new ConveyorSpeed(conveyorTalon,.4).withTimeout(.4) : new WaitCommand(0),
          new ParallelCommandGroup(new SetShooterSpeed(shooter, 6000),
                                   new SequentialCommandGroup(new WaitCommand(2), new ConveyorSpeed(conveyorTalon,-.6).withTimeout(3))).withTimeout(7),
          new SetShooterSpeed(shooter, 0).withTimeout(1),
          new ToggleIgnore(conveyorTalon, false));
  }
}
