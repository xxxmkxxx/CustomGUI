package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAnimation<N extends AbstractNode> implements Animation<N> {
    @Getter
    protected final List<AnimationFrameTimeStamp<N>> frames = new ArrayList<>();

    @Override
    public void addFrame(long timeUnit, AnimationFrame<N> frame) {
        frames.add(new AnimationFrameTimeStamp<>(timeUnit, frame));
    }
}
