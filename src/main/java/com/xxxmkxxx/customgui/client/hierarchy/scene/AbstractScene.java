package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;

import java.util.List;

public abstract class AbstractScene implements Scene {
    protected final RendererType type;
    protected List<AbstractNode> displays;

    public AbstractScene(RendererType type) {
        this.type = type;
    }

    @Override
    public void addElement(AbstractNode node) {
        node.initRenderer(type);
        displays.add(node);
    }

    @Override
    public void render() {
        displays.forEach(node -> node.getState().execute(node, node.getRenderer()));
    }
}
