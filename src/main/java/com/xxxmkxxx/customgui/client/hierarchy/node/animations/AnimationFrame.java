package com.xxxmkxxx.customgui.client.hierarchy.node.animations;

@FunctionalInterface
public interface AnimationFrame {
    void display();

    AnimationFrame EMPTY = () -> {};
}
