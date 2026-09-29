package org.firstinspires.ftc.teamcode.RobotHardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Subsystem for flywheel launcher motors.
 */
public class LauncherHardware {
    private DcMotor leftFlywheel = null;
    private DcMotor rightFlywheel = null;

    public void init(HardwareMap hardwareMap) {
        try {
            leftFlywheel = hardwareMap.get(DcMotor.class, "left_flywheel");
            rightFlywheel = hardwareMap.get(DcMotor.class, "right_flywheel");
            leftFlywheel.setDirection(DcMotor.Direction.REVERSE);
            rightFlywheel.setDirection(DcMotor.Direction.FORWARD);
            leftFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            // Hardware not configured yet
        }
    }

    public void setPower(double power) {
        if (leftFlywheel != null) leftFlywheel.setPower(power);
        if (rightFlywheel != null) rightFlywheel.setPower(power);
    }

    public void shoot(double power) {
        setPower(power);
    }

    public void stop() {
        setPower(0.0);
    }
}
