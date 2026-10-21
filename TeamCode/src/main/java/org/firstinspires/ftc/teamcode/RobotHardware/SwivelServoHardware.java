package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Subsystem for the Limelight swivel servo (tilting Limelight up at hive vs down at field).
 */
public class SwivelServoHardware {
    private Servo swivelServo = null;

    public static final double POSITION_UP_HIVE = 0.85;  // Looking up at hive / AprilTag
    public static final double POSITION_DOWN_FIELD = 0.15; // Looking down at field / blob detection

    public void init(HardwareMap hardwareMap) {
        try {
            swivelServo = hardwareMap.get(Servo.class, "swivel");
        } catch (Exception e) {
            // Hardware not configured yet
        }
    }

    public void setPosition(double position) {
        if (swivelServo != null) {
            swivelServo.setPosition(position);
        }
    }

    public void lookAtHive() {
        setPosition(POSITION_UP_HIVE);
    }

    public void lookAtField() {
        setPosition(POSITION_DOWN_FIELD);
    }
}
