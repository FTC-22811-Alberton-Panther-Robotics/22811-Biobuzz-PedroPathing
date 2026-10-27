package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;

/**
 * Direct path definitions generated for Hive Tipped Up side.
 * Converted to native PedroPathing 2.x classes (Pose, BezierLine, BezierCurve, Path).
 */
public class PathsHiveUp {

    private Pose pose(double x, double y, double deg) {
        return new Pose(x, y, Math.toRadians(deg));
    }

    public final Pose start = pose(57, 8.5, 90);
    public final Pose startToScore = pose(57, 14, 90);
    public final Pose scoreToGardenApproach = pose(24, 9.5, -179.4926);
    public final Pose scoreToGardenApproachControl1 = pose(57, 21, 0);
    public final Pose scoreToGardenApproachControl2 = pose(36.8, 9.5, 0);
    public final Pose gardenPickup = pose(9, 9, 180);
    public final Pose gardentoScore = pose(57, 14, 90);
    public final Pose gardentoScoreControl1 = pose(19.9708, 15.4878, 0);
    public final Pose scoretoGarden = pose(11.8275, 42, 179.4941);
    public final Pose scoretoGardenControl1 = pose(57, 30, 0);
    public final Pose scoretoGardenControl2 = pose(25.2052, 42, 0);
    public final Pose flowertoScore = pose(57, 14, 91.1173);
    public final Pose flowertoScoreControl1 = pose(25.4883, 42.2115, 0);
    public final Pose flowertoScoreControl2 = pose(57.0049, 29.8635, 0);
    public final Pose scoretoPark = pose(10, 90, 90.5391);
    public final Pose scoretoParkControl1 = pose(57, 28, 0);
    public final Pose scoretoParkControl2 = pose(10, 40, 0);

    public Path startToScore() {
        Path p = new Path(new BezierLine(start, startToScore));
        p.setLinearHeadingInterpolation(start.getHeading(), startToScore.getHeading());
        return p;
    }

    public Path scoreToGardenApproach() {
        Path p = new Path(new BezierCurve(startToScore, scoreToGardenApproachControl1, scoreToGardenApproachControl2, scoreToGardenApproach));
        p.setTangentHeadingInterpolation();
        return p;
    }

    public Path gardenPickup() {
        Path p = new Path(new BezierLine(scoreToGardenApproach, gardenPickup));
        p.setConstantHeadingInterpolation(gardenPickup.getHeading());
        return p;
    }

    public Path gardentoScore() {
        Path p = new Path(new BezierCurve(gardenPickup, gardentoScoreControl1, gardentoScore));
        p.setLinearHeadingInterpolation(gardenPickup.getHeading(), gardentoScore.getHeading());
        return p;
    }

    public Path scoretoGarden() {
        Path p = new Path(new BezierCurve(gardentoScore, scoretoGardenControl1, scoretoGardenControl2, scoretoGarden));
        p.setTangentHeadingInterpolation();
        return p;
    }

    public Path flowertoScore() {
        Path p = new Path(new BezierCurve(scoretoGarden, flowertoScoreControl1, flowertoScoreControl2, flowertoScore));
        p.setLinearHeadingInterpolation(scoretoGarden.getHeading(), flowertoScore.getHeading());
        return p;
    }

    public Path scoretoPark() {
        Path p = new Path(new BezierCurve(flowertoScore, scoretoParkControl1, scoretoParkControl2, scoretoPark));
        p.setTangentHeadingInterpolation();
        return p;
    }
}
