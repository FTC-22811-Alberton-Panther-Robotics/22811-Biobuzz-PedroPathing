package org.firstinspires.ftc.teamcode.InDevelopment;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware.RobotHardwareContainer;
import org.firstinspires.ftc.teamcode.behaviorTree.ActionNode;
import org.firstinspires.ftc.teamcode.behaviorTree.BehaviorNode;
import org.firstinspires.ftc.teamcode.behaviorTree.Sequence;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

/**
 * TeleOp OpMode combining PedroPathing teleop drive with Alan Smith-style Behavior Trees
 * for automated macros (intake, launching, scoring, limelight swivel).
 */
@TeleOp(name = "Morris Teleop (Behavior Tree + Pedro)", group = "01 Bozeman")
public class MorrisTeleop_BehaviorTree_Pedro extends OpMode {

    private RobotHardwareContainer robot;
    private BehaviorNode activeMacroTree;
    private final ElapsedTime macroTimer = new ElapsedTime();
    private boolean macroRunning = false;

    public static Pose startingPose;

    @Override
    public void init() {
        //robot = new RobotHardwareContainer(hardwareMap, telemetry);
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();

        telemetry.addData("Status", "Morris Teleop Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        // 1. PedroPathing TeleOp Driving
        double forward = -gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x/2;

        follower.setTeleOpDrive(forward, strafe, turn, true);
        follower.update();

        // 2. Behavior Tree Macro Triggering via Gamepad
        if (gamepad1.left_trigger_pressed) {
            // Trigger Intake Macro: tilt Limelight down to field and start intake
            activeMacroTree = buildIntakeMacro();
            macroRunning = true;
            macroTimer.reset();
        } else if (gamepad1.right_trigger_pressed) {
            // Trigger Launch/Score Macro: tilt Limelight up to hive, spin up flywheel, feed transfer
            activeMacroTree = buildLaunchMacro();
            macroRunning = true;
            macroTimer.reset();
        } else if (gamepad1.rightBumperWasPressed()) {
            // Cancel / Reset Macro and stop mechanisms
            activeMacroTree = null;
            macroRunning = false;
            robot.intake.stop();
            robot.launcher.stop();
            robot.transfer.reset();
        }

        // 3. Execute active Behavior Tree macro if running
        if (macroRunning && activeMacroTree != null) {
            BehaviorNode.Status status = activeMacroTree.execute();
            telemetry.addData("Macro Status", status);
            if (status == BehaviorNode.Status.SUCCESS || status == BehaviorNode.Status.FAILURE) {
                macroRunning = false;
            }
        } else {
            telemetry.addData("Macro Status", "Idle (Press A for Intake, B for Launch, X to Stop)");
        }

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }

    /**
     * Builds Behavior Tree macro for automated pollen/nectar intake.
     */
    private BehaviorNode buildIntakeMacro() {
        return new Sequence(
//                new ActionNode(() -> {
//                    // Tilt Limelight down to field for blob detection / viewing
//                    robot.limelight.prepareForBlobDetection();
//                    return BehaviorNode.Status.SUCCESS;
//                }),
                new ActionNode(() -> {
                    // Start intake motors
                    robot.intake.start();
                    macroTimer.reset();
                    return BehaviorNode.Status.SUCCESS;
                }),
                new ActionNode(() -> {
                    // Run intake for 1.5 seconds or until manually canceled
                    if (macroTimer.seconds() >= 1.5 || gamepad1.x) {
                        robot.intake.stop();
                        return BehaviorNode.Status.SUCCESS;
                    }
                    return BehaviorNode.Status.RUNNING;
                })
        );
    }

    /**
     * Builds Behavior Tree macro for launching / scoring into the hive.
     */
    private BehaviorNode buildLaunchMacro() {
        return new Sequence(
                new ActionNode(() -> {
                    // Tilt Limelight up to hive for AprilTag verification
                    robot.limelight.prepareForHiveDetection();
                    // Spin up flywheel
                    robot.launcher.shoot(0.85);
                    macroTimer.reset();
                    return BehaviorNode.Status.SUCCESS;
                }),
                new ActionNode(() -> {
                    // Wait 0.6 seconds for flywheel to spin up to speed
                    if (macroTimer.seconds() >= 0.6) {
                        return BehaviorNode.Status.SUCCESS;
                    }
                    return BehaviorNode.Status.RUNNING;
                }),
                new ActionNode(() -> {
                    // Feed transfer to launch pollen into hive
                    robot.transfer.feed();
                    macroTimer.reset();
                    return BehaviorNode.Status.SUCCESS;
                }),
                new ActionNode(() -> {
                    // Hold feed for 0.4 seconds, then stop launcher and reset transfer
                    if (macroTimer.seconds() >= 0.4) {
                        robot.launcher.stop();
                        robot.transfer.reset();
                        return BehaviorNode.Status.SUCCESS;
                    }
                    return BehaviorNode.Status.RUNNING;
                })
        );
    }
}
