package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.common.RenderTime;
import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.KeyboardManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSectionNodes;
import com.xxxmkxxx.timecontrol.TimeControl;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractScene implements Scene {
    @Getter
    protected final TimeControl renderTimeControl;
    @Getter
    protected RendererType type;
    @Getter
    protected final WindowSectionNodes windowSectionNodes;
    @Getter
    protected final TargetManager targetManager;
    @Getter
    protected final AnimationManager animationManager;
    @Getter
    protected final KeyboardManager keyboardManager;
    protected final Function<AbstractNode, WindowSection> INIT_NODE_SECTION_METHOD;
    protected final Consumer<AbstractNode> INIT_NODE_METHOD;

    public AbstractScene(RendererType type) {
        this.windowSectionNodes = new WindowSectionNodes();
        this.windowSectionNodes.init(new NodeFrameComparator());
        this.renderTimeControl = new TimeControl(new RenderTime());
        this.type = type;
        this.targetManager = new TargetManager(windowSectionNodes.getSections());
        this.animationManager = new AnimationManager(renderTimeControl);
        this.keyboardManager = new KeyboardManager(targetManager);
        this.INIT_NODE_SECTION_METHOD = targetManager::defineNodeSection;
        this.INIT_NODE_METHOD = node -> windowSectionNodes.addNode(node, node.getWindowSection());
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
        windowSectionNodes.getNodes().forEach(node -> node.getState().execute(node, node.getRenderer()));
    }

    public void updateTarget(int x, int y) {
        targetManager.update(x, y);
    }

    public List<AbstractNode> getAllNodes() {
        return windowSectionNodes.getNodes();
    }
}
