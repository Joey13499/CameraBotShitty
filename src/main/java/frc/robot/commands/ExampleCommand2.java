// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

// /** An example command that uses an example subsystem. */
// public class ExampleCommand extends Command {
//   @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
//   private final ExampleSubsystem m_subsystem;
//   private double x;
//   private double z;
//   /**
//    * Creates a new ExampleCommand.
//    *
//    * @param subsystem The subsystem used by this command.
//    */
//   public ExampleCommand(ExampleSubsystem subsystem, double x, double z) {
//     m_subsystem = subsystem;
//     this.x = x;
//     this.z = z;
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(subsystem);
//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     SmartDashboard.putNumber("Controller",x);
//     m_subsystem.drive(x,z);
//   }


// }

import frc.robot.subsystems.ExampleSubsystem2;

public class ExampleCommand2 extends Command {
    private final ExampleSubsystem2 m_subsystem;

    public ExampleCommand2(ExampleSubsystem2 subsystem) {
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        m_subsystem.trackTarget();
    }
}