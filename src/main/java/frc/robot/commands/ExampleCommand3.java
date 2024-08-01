// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem3;
import edu.wpi.first.wpilibj2.command.Command;


public class ExampleCommand3 extends Command {
    private final ExampleSubsystem3 m_subsystem;

    public ExampleCommand3(ExampleSubsystem3 subsystem) {
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        m_subsystem.takeSelfie();
    }
}