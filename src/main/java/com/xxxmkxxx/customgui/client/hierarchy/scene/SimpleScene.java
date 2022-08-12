package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;

import java.util.Arrays;
import java.util.LinkedList;

public class SimpleScene extends AbstractScene {
    public SimpleScene(RendererType type) {
        super(type);
        this.displays = new LinkedList<>();
    }

    public SimpleScene(RendererType type, AbstractNode ... displays) {
        super(type);
        this.displays = new LinkedList<>();
        Arrays.stream(displays).forEach(this::addElement);
    }
}
