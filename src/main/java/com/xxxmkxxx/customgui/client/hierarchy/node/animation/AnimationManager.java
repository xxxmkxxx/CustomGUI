package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public interface AnimationManager {
    <N extends AbstractNode> void addSimpleAnimation(N node, String animationName, AbstractAnimation<N> animation, int amountAnimationCycles);
    <N extends AbstractNode> void addStickyAnimation(N node, String animationName, AbstractAnimation<N> animation, int indexStickyFrame);
    void deleteStickyAnimation(String animationName);
    public <N extends AbstractNode> void addCyclicAnimation(N node, String animationName, AbstractAnimation<N> animation);
    void deleteCyclicAnimation(String animationName);
}
