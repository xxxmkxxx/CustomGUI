package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

@FunctionalInterface
public interface AnimationFrame {
    void display();

    AnimationFrame EMPTY = () -> {};
}
