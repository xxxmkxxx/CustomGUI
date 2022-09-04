package com.xxxmkxxx.customgui.client.ui.animations;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public class PermanentAnimation extends AbstractAnimation {
    private int phaseIndex = 0;

    public PermanentAnimation(AbstractNode node, AnimationPhase... phases) {
        super(node, phases);
    }

    public PermanentAnimation(AbstractNode node) {
        super(node);
    }

    @Override
    public void changePhase() {
        if (phaseIndex == phases.size()) phaseIndex = 0;

        currentPhase = phases.get(phaseIndex++);
    }
}
