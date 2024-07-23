
package frc.robot.subsystems;
import frc.robot.Constants;

import java.lang.Math;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import pabeles.concurrency.IntOperatorTask.Min;
import edu.wpi.first.math.controller.PIDController;
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
public class ExampleSubsystem2 extends SubsystemBase {
    private final TalonFX aimer;
    private final TalonFXConfiguration currentConfigurator;
    private final PIDController pidController;
    private final int kMinAngle;
    private final int kMaxAngle;
    private double offset;
    private double perSecondDecay;
    private double perSecondDecayPos;
    private double perSecondDecayNeg;
    private double direction;
    public ExampleSubsystem2(){
        aimer = new TalonFX(5);
        currentConfigurator = new TalonFXConfiguration();
        currentConfigurator.CurrentLimits.StatorCurrentLimit = 10;
        currentConfigurator.CurrentLimits.StatorCurrentLimitEnable = true;
        aimer.getConfigurator().apply(currentConfigurator);
        pidController = new PIDController(0.1, 0, 0);
        kMinAngle = -90;
        kMaxAngle = 90;
        offset = -aimer.getPosition().getValueAsDouble() * 360;
        perSecondDecay = 0;
        perSecondDecayNeg = 0;
        perSecondDecayPos = 0;
        direction = 0;
    }
    public void trackTarget(){
        double x = aimer.getPosition().getValueAsDouble();
        double currentAngle = x * 360.0 + offset;
        SmartDashboard.putNumber("aimerAngle",currentAngle);
        NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry txEntry = limelightTable.getEntry("tx");
        NetworkTableEntry tvEntry = limelightTable.getEntry("tv");
        double tx = -txEntry.getDouble(0.0);
        SmartDashboard.putNumber("tx",tx);
        int xValue = (int)tx;

        if (tvEntry.getDouble(0.0) == 1.0){
            if (xValue > 15){
                if (direction != 1){
                    if (perSecondDecayPos == 0){
                        perSecondDecayPos = 10;
                    } else {
                        perSecondDecayPos -= 1;
                        if (perSecondDecayPos == 0){
                            perSecondDecay = 0;
                            perSecondDecayNeg = 0;
                            direction = 1;
                        }
                    }
                } else {
                    direction = 1;
                }
            }
            else if (xValue < -15){
                if (direction != -1){
                    if (perSecondDecayNeg == 0){
                        perSecondDecayNeg = 10;
                    } else {
                        perSecondDecayNeg -= 1;
                        if (perSecondDecayNeg == 0){
                            perSecondDecay = 0;
                            perSecondDecayPos = 0;
                            direction = -1;
                        }
                    }
                } else {
                    direction = -1;
                }
            }
        } else {
            if (perSecondDecay == 0){
                perSecondDecay = 5;
            } else {
                perSecondDecay -= 1;
                if (perSecondDecay == 0){
                    perSecondDecayPos = 0;
                    perSecondDecayNeg = 0;
                    direction = 0;
                }
            }
        }
        aimer.setVoltage(direction * 0.2);
    }
    public void setOffset(){
        offset = -aimer.getPosition().getValueAsDouble() * 360;
    }
}
