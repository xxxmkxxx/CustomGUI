package com.xxxmkxxx.customgui.client.hierarchy.animations;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

@FunctionalInterface
public interface AnimationPhase {
    void display(AbstractNode node);
}
