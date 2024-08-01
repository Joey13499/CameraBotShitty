// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleSubsystem2 m_exampleSubsystem2 = new ExampleSubsystem2();
  private final ExampleSubsystem3 m_exampleSubsystem3 = new ExampleSubsystem3();
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final PS4Controller m_driverController =
      new PS4Controller(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_exampleSubsystem.setDefaultCommand(
      new ExampleCommand(
        m_exampleSubsystem, 
        m_driverController
      )
    );
    m_exampleSubsystem2.setDefaultCommand(
      new ExampleCommand2(m_exampleSubsystem2)
    );
    m_exampleSubsystem3.setDefaultCommand(
      new ExampleCommand3(m_exampleSubsystem3)
    );
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    new JoystickButton(m_driverController, 1).onTrue(
      new ParallelCommandGroup(
                new InstantCommand(() -> m_exampleSubsystem2.switchMode()),
                new InstantCommand(() -> m_exampleSubsystem3.switchMode())
            ) {{addRequirements(m_exampleSubsystem2,m_exampleSubsystem3);}}
    );
    new JoystickButton(m_driverController, 0).onTrue(
      new InstantCommand(() -> m_exampleSubsystem3.startSelfie())
    );
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
