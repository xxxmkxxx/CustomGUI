package com.xxxmkxxx.customgui.client.hierarchy.renderer;

import com.xxxmkxxx.customgui.client.hierarchy.node.Node;

public interface Renderer <N extends Node>{
    void render(N node);
}
