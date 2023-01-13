package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.common.RenderTime;
import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.node.KeyboardManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.Node;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.Section;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.timecontrol.TimeControl;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;

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
    @Getter
    protected final KeyboardManager keyboardManager;
    protected final Function<AbstractNode, Section> INIT_NODE_SECTION_METHOD;
    protected final Consumer<AbstractNode> INIT_NODE_METHOD;

    public AbstractScene(RendererType type) {
        Arrays.stream(Section.values()).forEach(section -> sections.put(section, new TreeSet<>(new NodeFrameComparator())));

        this.renderTimeControl = new TimeControl(new RenderTime());
        this.type = type;
        this.targetManager = new TargetManager(this.sections);
        this.animationManager = new AnimationManager(renderTimeControl);
        this.keyboardManager = new KeyboardManager(targetManager);
        this.INIT_NODE_SECTION_METHOD = targetManager::defineNodeSection;
        this.INIT_NODE_METHOD = node -> {
            System.out.println(node);
            sections.get(node.getSection()).add(node);
        };
    }

    @Override
    public void addElement(AbstractNode node) {
        node.initRenderer(type);
        node.initSection(INIT_NODE_SECTION_METHOD);
        node.init(INIT_NODE_METHOD);
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
