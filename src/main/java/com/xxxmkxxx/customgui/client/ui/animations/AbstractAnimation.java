package com.xxxmkxxx.customgui.client.ui.animations;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public abstract class AbstractAnimation implements Animation {
    protected final AbstractNode node;
    protected final List<AnimationPhase> phases;
    @Getter
    protected AnimationPhase currentPhase;
    @Getter @Setter
    protected int frameRate = 10;

    public AbstractAnimation(AbstractNode node, AnimationPhase ... phases) {
        this.node = node;
        this.phases = new ArrayList<>(Arrays.asList(phases));
        this.currentPhase = phases[0];
    }

    public AbstractAnimation(AbstractNode node) {
        this.node = node;
        phases = new ArrayList<>(10);
        this.currentPhase = AnimationPhases.EMPTY_PHASE;
    }

    @Override
    public void addPhase(AnimationPhase phase) {
        phases.add(phase);
    }

    @Override
    public void displayPhase() {
        currentPhase.display(node);
    }
}
