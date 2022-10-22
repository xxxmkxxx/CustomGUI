package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.common.RenderTime;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.NodeSection;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.timecontrol.TimeControl;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public abstract class AbstractScene implements Scene {
    @Getter
    protected final TimeControl renderTimeControl;
    @Getter
    protected final RendererType type;
    protected final EnumMap<NodeSection, List<AbstractNode>> sections = new EnumMap<>(NodeSection.class);
    @Getter
    protected final TargetManager targetManager;
    @Getter
    protected final AnimationManager animationManager;

    public AbstractScene(RendererType type) {
        Arrays.stream(NodeSection.values()).forEach(nodeSection -> sections.put(nodeSection, new ArrayList<>()));

        this.renderTimeControl = new TimeControl(new RenderTime());
        this.type = type;
        this.targetManager = new TargetManager();
        this.animationManager = new AnimationManager(renderTimeControl);
    }

    @Override
    public void addElement(AbstractNode node) {
        node.initRenderer(type);
        NodeSection section = targetManager.defineNodeSection(node);
        sections.get(section).add(node);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void render() {
        renderTimeControl.tick();
        sections.forEach((nodeSection, nodes) -> nodes.forEach(node -> node.getRenderer().render(node)));
    }

    public void updateTarget(int x, int y) {

    }
}
