package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.animations.AnimationManager;
import lombok.Getter;

import java.util.List;

public abstract class AbstractScene implements Scene {
    @Getter
    protected final RendererType type;
    protected List<AbstractNode> displays;
    @Getter
    protected final TargetManager targetManager;

    public AbstractScene(RendererType type) {
        this.type = type;
        this.targetManager = new TargetManager();
    }

    @Override
    public void addElement(AbstractNode node) {
        node.initRenderer(type);
        displays.add(node);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void render() {
        displays.forEach(node -> node.getState().execute(node, node.getRenderer()));
        AnimationManager.render();
    }

    public void updateTarget(int x, int y) {
        targetManager.setNode(null);

        displays.forEach(node -> node.updateTarget(x, y, targetManager));
    }
}
