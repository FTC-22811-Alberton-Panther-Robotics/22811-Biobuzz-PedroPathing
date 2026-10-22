package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.geometry.Pose;

/**
 * Centralized repository for field poses and coordinates for autonomous navigation.
 * Supports C1 (tipped up hive side) and C6 (tipped down hive side) starting positions,
 * hive scoring targets, garden pickup, and left flower (A2/3) locations.
 *
 * <p><b>Field Diagram Reference:</b></p>
 * <p>
 * <img src="field_layout.png" alt="FTC Field Layout and Coordinates" width="500"/>
 * </p>
 * <p><i>Tip: Use Android Studio's "Rendered Documentation" view (Ctrl+Q / F1 or click the gutter icon next to line 11) to view the rendered field image directly in the code editor!</i></p>
 */
public class FieldLocations {

    // Starting Poses (Red Alliance C1 and C6)
    public static final Pose START_HIVE_UP = new Pose(59.0, 8.5, Math.toRadians(0));
    public static final Pose START_HIVE_DOWN = new Pose(59.0, 133.5, Math.toRadians(0));

    // Hive Scoring / Tipping Poses
    public static final Pose HIVE_SCORE_C1 = new Pose(36.0, 36.0, Math.toRadians(90));
    public static final Pose HIVE_SCORE_C6 = new Pose(36.0, 96.0, Math.toRadians(90));

    // Garden & Flower Pickup Poses (A2/3)
    public static final Pose GARDEN_PICKUP = new Pose(60.0, 24.0, Math.toRadians(180));
    public static final Pose LEFT_FLOWER_A2_3 = new Pose(48.0, 48.0, Math.toRadians(135));

    // Staging / Waiting Poses
    public static final Pose WAITING_POSE_C6 = new Pose(24.0, 96.0, Math.toRadians(0));
}
