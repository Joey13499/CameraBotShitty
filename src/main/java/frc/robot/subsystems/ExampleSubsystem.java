// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ExampleSubsystem extends SubsystemBase {
  private final TalonFX frontLeft;
  private final TalonFX frontRight;
  private final TalonFX backLeft;
  private final TalonFX backRight;
  private final DifferentialDrive diffDrive;
  private final TalonFXConfiguration currentConfigurator;
  private double xDecay;

  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {
    frontLeft = new TalonFX(1);
    frontRight = new TalonFX(2);
    backLeft = new TalonFX(3);
    backRight = new TalonFX(4);
    currentConfigurator = new TalonFXConfiguration();
    currentConfigurator.CurrentLimits.StatorCurrentLimit = 15;
    currentConfigurator.CurrentLimits.StatorCurrentLimitEnable = true;
    frontLeft.getConfigurator().apply(currentConfigurator);
    frontRight.getConfigurator().apply(currentConfigurator);
    backLeft.getConfigurator().apply(currentConfigurator);
    backRight.getConfigurator().apply(currentConfigurator);
    frontLeft.setInverted(false);
    frontRight.setInverted(true);
    backLeft.setControl(new Follower(1, true));
    backRight.setControl(new Follower(2, true));
    backLeft.setInverted(false);
    backRight.setInverted(true);

    xDecay = 0.0;

    diffDrive = new DifferentialDrive(frontLeft, frontRight);
  }
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public void drive(double x, double y){
    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tvEntry = limelightTable.getEntry("tv");
    double tv = tvEntry.getDouble(0.0);
    SmartDashboard.putNumber("targetsGot", tv);
    int numberOfTargets = (int) tv;
    numberOfTargets = 0;
    if (numberOfTargets > 0){
      diffDrive.arcadeDrive(1,0);
    } else {
      diffDrive.arcadeDrive(x, y);
    }
    // voltageLeft = x*10;
    // voltageRight = x*10;
    // frontLeft.setControl(voltageRequest.withOutput(voltageLeft));
    // frontRight.setControl(voltageRequest.withOutput(voltageRight));

  }
  

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
