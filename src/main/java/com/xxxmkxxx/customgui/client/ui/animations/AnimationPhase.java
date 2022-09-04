package com.xxxmkxxx.customgui.client.ui.animations;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

@FunctionalInterface
public interface AnimationPhase {
    void display(AbstractNode node);
}
