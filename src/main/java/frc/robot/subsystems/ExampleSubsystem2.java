
package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

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
    private double perSecondDecayOn;
    private double direction;
    private boolean tracking;
    private boolean blinding;
    private NetworkTableEntry txEntry;
    private NetworkTableEntry tvEntry;
    private NetworkTableEntry ledEntry;
    private NetworkTable limelightTable;
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
        perSecondDecayOn = 0;
        direction = 0;
        tracking = true;
        blinding = true;
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        txEntry = limelightTable.getEntry("tx");
        tvEntry = limelightTable.getEntry("tv");
        ledEntry = limelightTable.getEntry("pipeline");
        ledEntry.setNumber(0);
    }
    public void trackTarget(){
        if (!blinding){
            return;
        }
        double x = aimer.getPosition().getValueAsDouble();
        double currentAngle = x * 360.0 + offset;
        SmartDashboard.putNumber("aimerAngle",currentAngle);
        
        double tx = txEntry.getDouble(0.0);
        SmartDashboard.putNumber("tx",tx);
        if (tracking){
            if (Math.abs(tx) > 10){
                aimer.setVoltage(tx*0.01);
                perSecondDecay = Math.min(perSecondDecay+1, 10);
            } else {
                aimer.setVoltage(0);
                if (tvEntry.getDouble(0.0) > 0){
                    ledEntry.setNumber(1);
                    tracking = false;
                    perSecondDecayOn = 200;
                }
            }
        } else {
            perSecondDecayOn -=1;
            if (perSecondDecayOn == 0){
                ledEntry.setNumber(0);
                tracking = true;
            }
        }
    }
    public void switchMode(){
        blinding = !blinding;
    }
    
}
