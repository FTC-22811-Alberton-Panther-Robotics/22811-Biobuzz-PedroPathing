package org.firstinspires.ftc.teamcode.InDevelopment;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotHardware.RobotHardwareContainer;
import org.firstinspires.ftc.teamcode.behaviorTree.ActionNode;
import org.firstinspires.ftc.teamcode.behaviorTree.BehaviorNode;
import org.firstinspires.ftc.teamcode.behaviorTree.Sequence;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.FieldLocations;
import org.firstinspires.ftc.teamcode.pedroPathing.GameState;

/**
 * Autonomous OpMode using PedroPathing and Alan Smith-style Behavior Trees (Iterative Structure).
 * Supports C1 (tipped up hive side) and C6 (tipped down hive side) starting positions.
 * Designed with placeholders for Limelight 3A integration.
 */
@Autonomous(name = "MorrisAuto_BehaviorTree_Pedro", group = "Autonomous")
public class MorrisAuto_BehaviorTree_Pedro extends OpMode {

    private Follower follower;
    private RobotHardwareContainer robot;
    private GameState gameState;
    private BehaviorNode rootTree;
    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        robot = new RobotHardwareContainer(hardwareMap, telemetry);
        gameState = new GameState();

        telemetry.addData("Status", "Initialized. Press X for C1 or B for C6.");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        if (gamepad1.x) {
            gameState.setStartingSide(GameState.StartingSide.C1_TIPPED_UP);
        } else if (gamepad1.b) {
            gameState.setStartingSide(GameState.StartingSide.C6_TIPPED_DOWN);
        }

        telemetry.addData("Selected Starting Side", gameState.getStartingSide() == GameState.StartingSide.C1_TIPPED_UP ? "C1 (Tipped Up Hive) [Press X]" : "C6 (Tipped Down Hive) [Press B]");
        telemetry.update();
    }

    @Override
    public void start() {
        // Set starting pose based on selection
        Pose startPose = (gameState.getStartingSide() == GameState.StartingSide.C1_TIPPED_UP) ? FieldLocations.START_HIVE_UP : FieldLocations.START_HIVE_DOWN;
        follower.setStartingPose(startPose);

        // Build Behavior Tree based on selected side
        rootTree = buildBehaviorTree();
        timer.reset();
    }

    @Override
    public void loop() {
        follower.update();

        // Execute Behavior Tree root node
        if (rootTree != null) {
            BehaviorNode.Status status = rootTree.execute();
            telemetry.addData("Behavior Tree Status", status);
        }

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }

    private BehaviorNode buildBehaviorTree() {
        if (gameState.getStartingSide() == GameState.StartingSide.C1_TIPPED_UP) {
            // C1 Strategy: Move to hive, shoot to tip, go to garden/left flower and intake
            return new Sequence(
                    new ActionNode(() -> {
                        // 1. Move to Hive C1
                        Path pathToHive = new Path(new BezierLine(FieldLocations.START_HIVE_UP, FieldLocations.HIVE_SCORE_C1));
                        pathToHive.setConstantHeadingInterpolation(0);
                        follower.followPath(pathToHive);
                        return BehaviorNode.Status.SUCCESS;
                    }),
                    new ActionNode(() -> {
                        // Wait until path completes
                        if (!follower.isBusy()) {
                            return BehaviorNode.Status.SUCCESS;
                        }
                        return BehaviorNode.Status.RUNNING;
                    }),
                    new ActionNode(() -> {
                        // 2. Shoot into hive to tip it
                        robot.launcher.shoot(0.8);
                        robot.transfer.feed();
                        timer.reset();
                        return BehaviorNode.Status.SUCCESS;
                    }),
                    new ActionNode(() -> {
                        // Wait 0.5s for action to complete
                        if (timer.seconds() >= 0.5) {
                            robot.launcher.stop();
                            robot.transfer.reset();
                            return BehaviorNode.Status.SUCCESS;
                        }
                        return BehaviorNode.Status.RUNNING;
                    }),
                    new ActionNode(() -> {
                        // 3. Move to Garden / Left Flower for pollen/nectar pickup
                        Path pathToGarden = new Path(new BezierLine(FieldLocations.HIVE_SCORE_C1, FieldLocations.GARDEN_PICKUP));
                        pathToGarden.setConstantHeadingInterpolation(0);
                        follower.followPath(pathToGarden);
                        robot.intake.start();
                        return BehaviorNode.Status.SUCCESS;
                    }),
                    new ActionNode(() -> {
                        if (!follower.isBusy()) {
                            robot.intake.stop();
                            return BehaviorNode.Status.SUCCESS;
                        }
                        return BehaviorNode.Status.RUNNING;
                    })
            );
        } else {
            // C6 Strategy: Watch hive tip (Limelight / Timer stub), wait 0.5s to settle, shoot, get more
            return new Sequence(
                    new ActionNode(() -> {
                        // 1. Move to waiting position near hive C6
                        Path pathToWait = new Path(new BezierLine(FieldLocations.START_HIVE_DOWN, FieldLocations.WAITING_POSE_C6));
                        pathToWait.setConstantHeadingInterpolation(0);
                        follower.followPath(pathToWait);
                        return BehaviorNode.Status.SUCCESS;
                    }),
                    new ActionNode(() -> {
                        if (!follower.isBusy()) {
                            return BehaviorNode.Status.SUCCESS;
                        }
                        return BehaviorNode.Status.RUNNING;
                    }),
                    new ActionNode(() -> {
                        // 2. Watch for hive to tip (Limelight or telemetry check stub)
                        // Limelight is set up to check AprilTag hive status
                        robot.limelight.prepareForHiveDetection();
                        timer.reset();
                        return BehaviorNode.Status.SUCCESS;
                    }),
                    new ActionNode(() -> {
                        // Wait for hive tip confirmation or timeout, then wait 0.5s for settling
                        boolean tipped = robot.limelight.isHiveTipped(telemetry) || timer.seconds() > 2.0;
                        if (tipped) {
                            // Wait half a second to let hive settle
                            if (timer.seconds() >= 2.5) {
                                return BehaviorNode.Status.SUCCESS;
                            }
                        }
                        return BehaviorNode.Status.RUNNING;
                    }),
                    new ActionNode(() -> {
                        // 3. Shoot pollen into hive
                        robot.launcher.shoot(0.8);
                        robot.transfer.feed();
                        timer.reset();
                        return BehaviorNode.Status.SUCCESS;
                    }),
                    new ActionNode(() -> {
                        if (timer.seconds() >= 0.5) {
                            robot.launcher.stop();
                            robot.transfer.reset();
                            return BehaviorNode.Status.SUCCESS;
                        }
                        return BehaviorNode.Status.RUNNING;
                    })
            );
        }
    }
}
