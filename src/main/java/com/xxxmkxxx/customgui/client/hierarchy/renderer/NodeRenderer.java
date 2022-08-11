package com.xxxmkxxx.customgui.client.hierarchy.renderer;

import com.xxxmkxxx.customgui.client.hierarchy.node.Node;

@FunctionalInterface
public interface NodeRenderer<N extends Node>{
    void render(N node);
}
