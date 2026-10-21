package org.firstinspires.ftc.teamcode.InDevelopment;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware.RobotHardwareContainer;

@TeleOp(name = "Simple Strafer TeleOp with Intake & Launcher", group = "TeleOp")
public class TeleOpModeThatWorksHopfully extends OpMode {

    // Drive motors
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    // Robot hardware container for intake, launcher, transfer, etc.
    private RobotHardwareContainer robot;

    @Override
    public void init() {
        // Initialize drive motors
        // Note: Update motor name strings if your configuration uses different names
        try {
            leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
            rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
            leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
            rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

            // Set motor directions (reverse left side or right side depending on your chassis build)
            leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
            leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
            rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
            rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

            leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e) {
            telemetry.addData("Drive Init Warning", "Some drive motors not found in config.");
        }

        // Initialize robot hardware container (intake, launcher, transfer, etc.)
        robot = new RobotHardwareContainer(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized. Ready to start!");
        telemetry.update();
    }

    @Override
    public void loop() {
        // -------------------------------------------------------------
        // 1. MECANUM / STRAFER DRIVING CONTROLS
        // -------------------------------------------------------------
        // Left Stick Y = Forward/Backward (-y because joystick up is negative)
        // Left Stick X = Strafe Left/Right
        // Right Stick X = Rotation
        double axial   = -gamepad1.left_stick_y;
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        // Combine movement parameters for mecanum wheel power formulas
        double rawLeftFront  = axial + lateral + yaw;
        double rawRightFront = axial - lateral - yaw;
        double rawLeftBack   = axial - lateral + yaw;
        double rawRightBack  = axial + lateral - yaw;

        // Normalize wheel powers so none exceed 1.0 while maintaining proportions
        double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(rawLeftFront), Math.abs(rawRightFront)),
                Math.max(Math.abs(rawLeftBack), Math.abs(rawRightBack))
        ));

        double leftFrontPower  = rawLeftFront / max;
        double rightFrontPower = rawRightFront / max;
        double leftBackPower   = rawLeftBack / max;
        double rightBackPower  = rawRightBack / max;

        // Apply power to drive motors if initialized
        if (leftFrontDrive != null) leftFrontDrive.setPower(leftFrontPower);
        if (rightFrontDrive != null) rightFrontDrive.setPower(rightFrontPower);
        if (leftBackDrive != null) leftBackDrive.setPower(leftBackPower);
        if (rightBackDrive != null) rightBackDrive.setPower(rightBackPower);

        // -------------------------------------------------------------
        // 2. INTAKE CONTROLS
        // -------------------------------------------------------------
        // Hold Left Bumper to Intake In, Hold Left Trigger to Outtake
        if (gamepad1.left_bumper) {
            robot.intake.setPower(0.85); // Adjust power as needed (0.0 to 1.0)
        } else {
            robot.intake.stop();
        }

        // -------------------------------------------------------------
        // 3. LAUNCHER & TRANSFER CONTROLS
        // -------------------------------------------------------------
        // Hold Right Bumper to spin up launcher flywheels
        // Press A to feed game piece using transfer servo
        if (gamepad1.right_bumper) {
            robot.launcher.shoot(0.85); // Adjust power as needed (0.0 to 1.0)
        } else {
            robot.launcher.stop();
        }

        if (gamepad1.a) {
            robot.transfer.feed();
        } else {
            robot.transfer.reset();
        }

        // -------------------------------------------------------------
        // TELEMETRY FEEDBACK
        // -------------------------------------------------------------
        telemetry.addData("Drive", "Axial: %.2f, Lateral: %.2f, Yaw: %.2f", axial, lateral, yaw);
        telemetry.addData("Intake", gamepad1.left_bumper ? "Running In" : (gamepad1.left_trigger > 0.1 ? "Outtaking" : "Stopped"));
        telemetry.addData("Launcher", gamepad1.right_bumper ? "Shooting (0.85)" : "Stopped");
        telemetry.addData("Transfer", gamepad1.a ? "Feeding" : "Reset");
        telemetry.update();
    }

    @Override
    public void stop() {
        // Safe stop all mechanisms
        if (leftFrontDrive != null) leftFrontDrive.setPower(0);
        if (rightFrontDrive != null) rightFrontDrive.setPower(0);
        if (leftBackDrive != null) leftBackDrive.setPower(0);
        if (rightBackDrive != null) rightBackDrive.setPower(0);
        if (robot != null) {
            robot.intake.stop();
            robot.launcher.stop();
            robot.transfer.reset();
        }
    }
}
