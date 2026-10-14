package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This class acts as a container to hold and initialize all the robot's mechanical hardware subsystems.
 * Includes intake, transfer, launcher, swivel servo, and Limelight 3A sensor.
 */
public class RobotHardwareContainer {

    public final IntakeHardware intake;
    public final TransferHardware transfer;
    public final LauncherHardware launcher;
    public final SwivelServoHardware swivelServo;
    public final LimelightHardware limelight;



    public RobotHardwareContainer(HardwareMap hardwareMap, Telemetry telemetry) {
        intake = new IntakeHardware();
        transfer = new TransferHardware();
        launcher = new LauncherHardware();
        swivelServo = new SwivelServoHardware();
        limelight = new LimelightHardware();

        intake.init(hardwareMap);
        transfer.init(hardwareMap);
        launcher.init(hardwareMap);
        swivelServo.init(hardwareMap);
        limelight.init(hardwareMap, swivelServo);
    }
}
