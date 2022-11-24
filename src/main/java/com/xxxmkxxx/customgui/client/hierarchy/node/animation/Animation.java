package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

@SuppressWarnings("unused")
public interface Animation<N extends AbstractNode> {
    void addFrame(long timeUnit, AnimationFrame<N> frame);
}
