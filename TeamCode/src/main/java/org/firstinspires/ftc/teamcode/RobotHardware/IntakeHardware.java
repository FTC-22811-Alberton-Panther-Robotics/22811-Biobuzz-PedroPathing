package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Subsystem for collecting pollen and nectar on the field.
 */
public class IntakeHardware {
    private DcMotor intakeMotor = null;

    public void init(HardwareMap hardwareMap) {
        try {
            intakeMotor = hardwareMap.get(DcMotor.class, "intake_motor");
            intakeMotor.setDirection(DcMotor.Direction.FORWARD);
            intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            // Hardware not configured yet

        }
    }

    public void setPower(double power) {
        if (intakeMotor != null) {
            intakeMotor.setPower(power);
        }
    }

    public void start() {
        setPower(1.0);
    }

    public void stop() {
        setPower(0.0);
    }
}
