package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard;

import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;

public class StandardInputFieldAnimation extends AbstractAnimation {
    public StandardInputFieldAnimation(String name) {
        super(name);

        addFrame(0, () -> {});
        addFrame(20, () -> {});
        addFrame(40, () -> {});
    }
}
