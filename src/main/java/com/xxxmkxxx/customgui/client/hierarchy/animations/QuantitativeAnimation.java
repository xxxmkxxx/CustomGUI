package com.xxxmkxxx.customgui.client.hierarchy.animations;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public class QuantitativeAnimation extends AbstractAnimation {
    private int phaseIndex = 0;
    private int currentAmountAnimationCycles = 0;
    private final int amountAnimationCycles;

    public QuantitativeAnimation(AbstractNode node, int amountAnimationCycles, AnimationPhase... phases) {
        super(node, phases);
        this.amountAnimationCycles = amountAnimationCycles;
    }

    public QuantitativeAnimation(AbstractNode node, int amountAnimationCycles) {
        super(node);
        this.amountAnimationCycles = amountAnimationCycles;
    }

    @Override
    public void changePhase() {
        if (phaseIndex == phases.size()) {
            phaseIndex = 0;
            currentAmountAnimationCycles++;
        }

        if (amountAnimationCycles < currentAmountAnimationCycles) {
            currentPhase = phases.get(phaseIndex++);
        }
    }
}
