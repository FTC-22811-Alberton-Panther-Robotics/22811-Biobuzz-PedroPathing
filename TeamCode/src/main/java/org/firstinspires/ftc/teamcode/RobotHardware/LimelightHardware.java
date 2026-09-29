package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Subsystem for Limelight 3A sensor, pipeline setup, AprilTag reading (hive tip confirmation),
 * and blob detection (pollen/nectar collection).
 * Designed to support future adaptive auto integration while supporting initial preplanned auto.
 */
public class LimelightHardware {
    private Limelight3A limelight = null;
    private SwivelServoHardware swivelServo = null;

    public enum Pipeline {
        APRIL_TAG_HIVE(0),
        BLOB_DETECTION(1);

        private final int id;
        Pipeline(int id) { this.id = id; }
        public int getId() { return id; }
    }

    public void init(HardwareMap hardwareMap, SwivelServoHardware swivelServo) {
        this.swivelServo = swivelServo;
        try {
            limelight = hardwareMap.get(Limelight3A.class, "limelight");
            limelight.pipelineSwitch(Pipeline.APRIL_TAG_HIVE.getId());
            limelight.start();
        } catch (Exception e) {
            // Hardware not configured yet
        }
    }

    public void setPipeline(Pipeline pipeline) {
        if (limelight != null) {
            limelight.pipelineSwitch(pipeline.getId());
        }
    }

    public void prepareForHiveDetection() {
        if (swivelServo != null) {
            swivelServo.lookAtHive();
        }
        setPipeline(Pipeline.APRIL_TAG_HIVE);
    }

    public void prepareForBlobDetection() {
        if (swivelServo != null) {
            swivelServo.lookAtField();
        }
        setPipeline(Pipeline.BLOB_DETECTION);
    }

    public boolean isHiveTipped(Telemetry telemetry) {
        // Stub for Limelight AprilTag hive tip detection.
        if (limelight != null) {
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                if (telemetry != null) {
                    telemetry.addData("Limelight Target", "Valid (Tx: %.2f)", result.getTx());
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasPollenDetected(Telemetry telemetry) {
        // Stub for blob detection pipeline
        if (limelight != null) {
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                return true;
            }
        }
        return false;
    }
}
