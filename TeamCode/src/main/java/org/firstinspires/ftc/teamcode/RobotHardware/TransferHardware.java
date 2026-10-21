package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Subsystem for transfer / launch initiation mechanism (feeding pollen into the launcher).
 */
public class TransferHardware {
    private CRServo transferServo = null;

    public void init(HardwareMap hardwareMap) {
        try {
            transferServo = hardwareMap.get(CRServo.class, "transfer");
        } catch (Exception e) {
            // Hardware not configured yet
        }
    }

    public void setPower(double power) {
        if (transferServo != null) {
            transferServo.setPower(power);
        }
    }

    public void feed() {
        setPower(0.8);
    }

    public void reset() {
        setPower(0.0);
    }

    public void reverse(){setPower(0.8);}
}
