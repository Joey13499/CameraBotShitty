// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.subsystems.ExampleSubsystem;

public class ExampleCommand extends Command {
    private final ExampleSubsystem m_subsystem;
    private final PS4Controller m_controller;

    public ExampleCommand(ExampleSubsystem subsystem, PS4Controller controller) {
        m_subsystem = subsystem;
        m_controller = controller;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        double rightY = m_controller.getRawAxis(PS4Controller.Axis.kRightY.value);
        double leftX = m_controller.getRawAxis(PS4Controller.Axis.kLeftX.value);
        SmartDashboard.putNumber("y",rightY);
        SmartDashboard.putNumber("x",leftX);
        // Use rightY and rightX to control your subsystem
        m_subsystem.drive(rightY, leftX);
    }
}