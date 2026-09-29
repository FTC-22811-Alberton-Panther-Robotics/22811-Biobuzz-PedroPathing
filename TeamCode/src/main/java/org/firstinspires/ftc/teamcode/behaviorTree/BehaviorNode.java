package org.firstinspires.ftc.teamcode.behaviorTree;

/**
 * Base BehaviorNode class for Behavior Trees as taught in Alan Smith's "Learn Java for FTC".
 * Represents a node in the behavior tree that executes and returns a Status.
 */
public abstract class BehaviorNode {

    public enum Status {
        SUCCESS,
        FAILURE,
        RUNNING
    }

    /**
     * Executes the behavior node logic.
     * @return Status of execution (SUCCESS, FAILURE, or RUNNING)
     */
    public abstract Status execute();
}
