package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;

public interface Node {
    void hide();
    void display();
    void initRenderer(RendererType type);
}
