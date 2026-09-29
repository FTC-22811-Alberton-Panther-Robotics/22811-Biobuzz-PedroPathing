# PedroPathing + BehaviorTree Adaptive Auto Implementation Plan

This plan outlines the architecture and implementation steps for building an adaptive autonomous routine combining PedroPathing and Behavior Trees (following Alan Smith's *Learn Java for FTC*), modular hardware subsystems (Limelight 3A with swivel servo, intake, transfer, launcher), field locations/poses, and initial C1/C6 autonomous execution (ignoring Limelight initially while leaving robust integration points).

## User Review Required

> [!IMPORTANT]
> - **Behavior Tree Framework**: Implements a standard Alan Smith-style Behavior Tree architecture (`Status`, `BehaviorNode`, `Sequence`, `Selector`, Action nodes).
> - **Hardware Subsystems**: Modular classes for Intake, Transfer, Launcher, Swivel Servo, and Limelight 3A (with pipeline/swivel controls stubbed for future vision integration).
> - **Field Locations & Game State**: Centralized pose management for C1 and C6 starting positions, hives, garden, and flowers.
> - **Initial Auto OpMode**: Implements C1 (tipped up hive side: move and shoot to tip, pick up pollen/nectar) and C6 (tipped down hive side: wait for hive tip via telemetry/stub, shoot and score) using PedroPathing and Behavior Trees.

## Open Questions

- None at this stage. The structure matches the requested Alan Smith Behavior Tree pattern and PedroPathing integration.

## Proposed Changes

### Behavior Tree Framework
#### [NEW] [BehaviorNode.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/behaviorTree/BehaviorNode.java)
- Defines `Status` enum (`SUCCESS`, `FAILURE`, `RUNNING`) and abstract `execute()` method.

#### [NEW] [Sequence.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/behaviorTree/Sequence.java)
- Composite node executing child nodes in sequence until one fails or runs.

#### [NEW] [Selector.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/behaviorTree/Selector.java)
- Composite node executing child nodes until one succeeds or runs.

#### [NEW] [ActionNode.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/behaviorTree/ActionNode.java)
- Leaf node wrapper for functional actions (lambdas/runnables).

---

### Robot Hardware Subsystems
#### [NEW] [IntakeHardware.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware/IntakeHardware.java)
- Subsystem for intake motors/servos (pollen/nectar collection).

#### [NEW] [TransferHardware.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware/TransferHardware.java)
- Subsystem for transfer / launch initiation mechanism.

#### [NEW] [LauncherHardware.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware/LauncherHardware.java)
- Subsystem for flywheel launcher motors.

#### [NEW] [SwivelServoHardware.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware/SwivelServoHardware.java)
- Subsystem for the limelight swivel servo (pointing up at hive vs down at field).

#### [NEW] [LimelightHardware.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware/LimelightHardware.java)
- Subsystem for Limelight 3A sensor, pipeline setup, AprilTag tracking, blob detection, and swivel coordination (with ignore/stub mode for initial auto).

#### [MODIFY] [RobotHardwareContainer.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/RobotHardware/RobotHardwareContainer.java)
- Wires up all new hardware subsystems (`intake`, `transfer`, `launcher`, `swivelServo`, `limelight`).

---

### Field Poses & Game State
#### [NEW] [FieldLocations.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/FieldLocations.java)
- Defines starting poses for C1 and C6, hive scoring positions, garden, and left flower (A2/3) poses.

#### [NEW] [GameState.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/pedroPathing/GameState.java)
- Tracks match state (starting side C1/C6, hive tip status, score count, etc.).

---

### Autonomous OpMode
#### [NEW] [AutoC1_C6_BehaviorTree.java](file:///C:/Users/nathan.morris/StudioProjects/22811-Biobuzz-PedroPathing/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/autonomously/AutoC1_C6_BehaviorTree.java)
- LinearOpMode using PedroPathing Follower, RobotHardwareContainer, and Behavior Tree. Supports starting position selection (C1 vs C6), executing preplanned paths, running intake, launcher, and transfer, with hooks for future Limelight integration.

## Verification Plan

### Automated Tests
- Build project using Gradle (`gradle_build`) to ensure no compilation errors.

### Manual Verification
- Deploy to robot / simulator via Android Studio.
- Verify OpMode appears on Driver Station, starting positions C1/C6 can be selected, and autonomous behavior tree runs successfully.
