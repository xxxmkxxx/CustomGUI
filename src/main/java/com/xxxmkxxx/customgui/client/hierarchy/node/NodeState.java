package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.Renderer;

@FunctionalInterface
public interface NodeState<N extends Node> {
    void execute(N node, Renderer<N> renderer);
}
