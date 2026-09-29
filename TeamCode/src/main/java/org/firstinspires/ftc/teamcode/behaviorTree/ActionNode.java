package org.firstinspires.ftc.teamcode.behaviorTree;

import java.util.function.Supplier;

/**
 * ActionNode leaf node in the Behavior Tree.
 * Executes a functional action or supplier returning a Status.
 */
public class ActionNode extends BehaviorNode {

    private final Supplier<Status> action;

    public ActionNode(Supplier<Status> action) {
        this.action = action;
    }

    @Override
    public Status execute() {
        if (action != null) {
            return action.get();
        }
        return Status.FAILURE;
    }
}
