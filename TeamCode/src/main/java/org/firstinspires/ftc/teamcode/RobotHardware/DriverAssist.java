package org.firstinspires.ftc.teamcode.RobotHardware;

import com.pedropathing.geometry.Pose;

public class DriverAssist {
    private final Follower follower;
    public enum DriveMode {
        ROBOT_CENTRIC,
        FIELD_CENTRIC,
        FC_TARGET_LOCK,
        RC_TARGET_LOCK
    }


    private DriveMode currentMode = DriveMode.ROBOT_CENTRIC;

    // Proportional gain for the Target Lock mode.
    // Tune this for responsive but stable target locking.
    private static final double HEADING_KP = 0.4;

    private static final double TURNING_MULTIPLIER = 0.5; // limit turning power for more control

    public DriverAssist(Follower follower) {
        this.follower = follower;
    }

    public void setMode(DriveMode mode) {
        this.currentMode = mode;
    }

    public DriveMode getMode() {
        return currentMode;
    }






    public void update(double joyY, double joyX, double joyTurn) {
        Pose robotPose = follower.getPose();
        double headingError = 0;
        double calculatedTurn = 0;
        joyTurn = squareInputWithSign(joyTurn) * TURNING_MULTIPLIER; // Limit turning power for more control
        joyY = squareInputWithSign(joyY);
        joyX = squareInputWithSign(joyX);
    }
}
