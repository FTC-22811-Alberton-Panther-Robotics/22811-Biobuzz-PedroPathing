package org.firstinspires.ftc.teamcode.behaviorTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Selector composite node (Fallback). Executes child nodes in order until one succeeds.
 * If any child returns SUCCESS, the selector returns SUCCESS.
 * If any child returns RUNNING, the selector returns RUNNING.
 * If all children return FAILURE, the selector returns FAILURE.
 */
public class Selector extends BehaviorNode {

    private final List<BehaviorNode> children = new ArrayList<>();
    private int currentChildIndex = 0;

    public Selector(BehaviorNode... children) {
        this.children.addAll(Arrays.asList(children));
    }

    public Selector addChild(BehaviorNode node) {
        children.add(node);
        return this;
    }

    @Override
    public Status execute() {
        if (children.isEmpty()) {
            return Status.FAILURE;
        }

        while (currentChildIndex < children.size()) {
            BehaviorNode child = children.get(currentChildIndex);
            Status status = child.execute();

            if (status == Status.RUNNING) {
                return Status.RUNNING;
            }

            if (status == Status.SUCCESS) {
                currentChildIndex = 0; // reset for next execution
                return Status.SUCCESS;
            }

            currentChildIndex++;
        }

        currentChildIndex = 0; // reset after all failed
        return Status.FAILURE;
    }
}
