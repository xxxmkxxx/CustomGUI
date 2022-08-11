package com.xxxmkxxx.customgui.client.hierarchy.renderer;

import com.xxxmkxxx.customgui.client.hierarchy.node.Node;

public interface RendererFactory <N extends Node> {
    NodeRenderer<N> create(RendererType type);
}
