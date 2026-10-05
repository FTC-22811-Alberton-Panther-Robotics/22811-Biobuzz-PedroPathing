package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Subsystem for transfer / launch initiation mechanism (feeding pollen into the launcher).
 */
public class TransferHardware {
    private Servo transferServo = null;

    public void init(HardwareMap hardwareMap) {
        try {
            transferServo = hardwareMap.get(Servo.class, "transfer_servo");
        } catch (Exception e) {
            // Hardware not configured yet
        }
    }

    public void setPosition(double position) {
        if (transferServo != null) {
            transferServo.setPosition(position);
        }
    }

    public void feed() {
        setPosition(0.8);
    }

    public void reset() {
        setPosition(0.0);
    }
}
