package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

import java.util.HashMap;
import java.util.Map;

public abstract class StandardAnimations<N extends AbstractNode> {
    protected Map<String, AbstractAnimation<N>> animations = new HashMap<>();

    public AbstractAnimation<N> get(String animationName) {
        return animations.get(animationName);
    }

    public void add(String animationName, AbstractAnimation<N> animation) {
        animations.put(animationName, animation);
    }
}
