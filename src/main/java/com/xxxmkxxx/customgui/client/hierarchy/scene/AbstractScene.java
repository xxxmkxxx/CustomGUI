package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.common.RenderTime;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.timecontrol.TimeControl;
import lombok.Getter;

import java.util.List;

public abstract class AbstractScene implements Scene {
    @Getter
    protected final TimeControl renderTimeControl;
    @Getter
    protected final RendererType type;
    protected List<AbstractNode> displays;
    @Getter
    protected final TargetManager targetManager;
    @Getter
    protected final AnimationManager animationManager;

    public AbstractScene(RendererType type) {
        this.renderTimeControl = new TimeControl(new RenderTime());
        this.type = type;
        this.targetManager = new TargetManager();
        this.animationManager = new AnimationManager(renderTimeControl);
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
        renderTimeControl.tick();
    }

    public void updateTarget(int x, int y) {
        targetManager.setCurrentSelection(null);

        displays.forEach(node -> node.updateTarget(x, y, targetManager));
    }
}
