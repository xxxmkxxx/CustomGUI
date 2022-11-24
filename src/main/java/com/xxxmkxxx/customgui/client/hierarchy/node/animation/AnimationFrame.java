package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

@FunctionalInterface
public interface AnimationFrame<N extends AbstractNode> {
    void display(N node);
}
