
package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

import java.lang.Math;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


// import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class ExampleSubsystem3 extends SubsystemBase {
    private NetworkTableEntry txEntry;
    private NetworkTableEntry tvEntry;
    private NetworkTableEntry ledEntry;
    private NetworkTable limelightTable;
    private boolean selfie;
    private boolean activeSelfie;
    private double timer;
    public ExampleSubsystem3(){
        selfie = false;
        activeSelfie = false;
        timer = 0;
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        txEntry = limelightTable.getEntry("tx");
        tvEntry = limelightTable.getEntry("tv");
        ledEntry = limelightTable.getEntry("pipeline");
        ledEntry.setNumber(0);
    }
    public void takeSelfie(){
        if (!selfie || !activeSelfie){
            return;
        }
        if (timer==0){
            timer = (int)System.currentTimeMillis() + 5000;
        }
        int current = (int)System.currentTimeMillis();
        SmartDashboard.putNumber("SMILE :)", (int)((current - timer)/1000));
        if (current == timer){
            LimelightHelpers.takeSnapshot("limelight", "newSelfie");
            timer = 0;
            activeSelfie = false;
        }
    }
    public void switchMode(){
        selfie = !selfie;
    }
    public void startSelfie(){
        if (selfie && !activeSelfie){
            activeSelfie = true;
        }
    }
}
