package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import lombok.Getter;

import java.util.*;

public abstract class AbstractAnimation implements Animation {
    @Getter
    private final String name;
    @Getter
    protected final List<AnimationFrameTimeStamp> frames = new ArrayList<>();

    public AbstractAnimation(String name) {
        this.name = name;
    }

    @Override
    public void addFrame(long timeUnit, AnimationFrame frame) {
        frames.add(new AnimationFrameTimeStamp(timeUnit, frame));
    }
}
