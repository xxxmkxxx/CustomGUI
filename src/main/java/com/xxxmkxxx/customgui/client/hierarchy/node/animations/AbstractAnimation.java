package com.xxxmkxxx.customgui.client.hierarchy.node.animations;

import lombok.Getter;

import java.util.*;

public abstract class AbstractAnimation implements Animation {
    @Getter
    private final String name;
    @Getter
    protected final List<AnimationFrameTimeStamp> frames = new ArrayList<>();
    @Getter
    protected final int amountAnimationPlays;
    @Getter
    protected int currentAmountAnimationPlays = 0;

    public AbstractAnimation(String name, int amountAnimationPlays) {
        this.name = name;
        this.amountAnimationPlays = amountAnimationPlays;
    }

    @Override
    public void addFrame(int timeUnit, AnimationFrame frame) {
        frames.add(new AnimationFrameTimeStamp(timeUnit, frame));
    }
}
