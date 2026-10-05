package org.firstinspires.ftc.teamcode.behaviorTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sequence composite node. Executes child nodes in order.
 * If any child returns FAILURE, the sequence returns FAILURE.
 * If any child returns RUNNING, the sequence returns RUNNING.
 * If all children return SUCCESS, the sequence returns SUCCESS.
 */
public class Sequence extends BehaviorNode {

    private final List<BehaviorNode> children = new ArrayList<>();
    private int currentChildIndex = 0;

    public Sequence(BehaviorNode... children) {
        this.children.addAll(Arrays.asList(children));
    }

    public Sequence addChild(BehaviorNode node) {
        children.add(node);
        return this;
    }

    @Override
    public Status execute() {
        if (children.isEmpty()) {
            return Status.SUCCESS;
        }

        while (currentChildIndex < children.size()) {
            BehaviorNode child = children.get(currentChildIndex);
            Status status = child.execute();

            if (status == Status.RUNNING) {
                return Status.RUNNING;
            }

            if (status == Status.FAILURE) {
                currentChildIndex = 0; // reset for next execution
                return Status.FAILURE;
            }

            currentChildIndex++;
        }

        currentChildIndex = 0; // reset after successful completion
        return Status.SUCCESS;
    }
}
