package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.common.RenderTime;
import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.Section;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;
import com.xxxmkxxx.timecontrol.TimeControl;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractScene implements Scene {
    @Getter
    protected final TimeControl renderTimeControl;
    @Getter
    protected final RendererType type;
    protected final EnumMap<Section, Set<AbstractNode>> sections = new EnumMap<>(Section.class);
    @Getter
    protected final TargetManager targetManager;
    @Getter
    protected final AnimationManager animationManager;
    protected AbstractNode lastNode = null;

    public AbstractScene(RendererType type) {
        Arrays.stream(Section.values()).forEach(section -> sections.put(section, new TreeSet<>(new NodeFrameComparator())));

        this.renderTimeControl = new TimeControl(new RenderTime());
        this.type = type;
        this.targetManager = new TargetManager(this.sections);
        this.animationManager = new AnimationManager(renderTimeControl);
    }

    @Override
    public void addElement(AbstractNode node) {
        node.initRenderer(type);

        Section section = targetManager.defineNodeSection(node);
        sections.get(section).add(node);
        lastNode = node;

        System.out.println(section + "- " + ((AbstractButton)node).getName().getString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void render() {
        renderTimeControl.tick();
        sections.forEach((section, nodes) -> nodes.forEach(node -> node.getRenderer().render(node)));
    }

    public void updateTarget(int x, int y) {
        targetManager.update(x, y);
    }
}
