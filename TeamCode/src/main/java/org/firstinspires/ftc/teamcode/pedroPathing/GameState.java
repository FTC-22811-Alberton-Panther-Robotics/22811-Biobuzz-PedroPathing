package org.firstinspires.ftc.teamcode.pedroPathing;

/**
 * Tracks the state of the robot and match during autonomous execution,
 * such as starting position (C1 vs C6), hive tip status, and score progress.
 */
public class GameState {

    public enum StartingSide {
        C1_TIPPED_UP,
        C6_TIPPED_DOWN
    }

    private StartingSide startingSide = StartingSide.C1_TIPPED_UP;
    private boolean hiveTipped = false;
    private int pollenCollectedCount = 0;
    private int scoreCount = 0;

    public StartingSide getStartingSide() {
        return startingSide;
    }

    public void setStartingSide(StartingSide startingSide) {
        this.startingSide = startingSide;
    }

    public boolean isHiveTipped() {
        return hiveTipped;
    }

    public void setHiveTipped(boolean hiveTipped) {
        this.hiveTipped = hiveTipped;
    }

    public int getPollenCollectedCount() {
        return pollenCollectedCount;
    }

    public void incrementPollenCount() {
        this.pollenCollectedCount++;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public void incrementScoreCount() {
        this.scoreCount++;
    }
}
