package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;

import java.util.Arrays;

public class SimpleScene extends AbstractScene {
    public SimpleScene(RendererType type) {
        super(type);
    }

    public SimpleScene(RendererType type, AbstractNode ... displays) {
        super(type);
        Arrays.stream(displays).forEach(this::addElement);
    }
}
