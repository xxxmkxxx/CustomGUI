package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.common.RenderTime;
import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.keyboard.AbstractKeyboardManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.keyboard.SimpleKeyboardManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.AbstractTargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.SimpleTargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSectionNodes;
import com.xxxmkxxx.timecontrol.TimeControl;
import lombok.Getter;

import java.util.List;

public abstract class AbstractScene implements Scene {
    public static AbstractScene EMPTY_SCENE = new AbstractScene(RendererType.SCREEN) {
        @Override
        public TimeControl getRenderTimeControl() {
            return this.renderTimeControl;
        }

        @Override
        public RendererType getType() {
            return super.getType();
        }

        @Override
        public WindowSectionNodes getWindowSectionNodes() {
            return super.getWindowSectionNodes();
        }

        @Override
        public AbstractTargetManager getTargetManager() {
            return AbstractTargetManager.EMPTY_TARGET_MANAGER;
        }

        @Override
        public AnimationManager getAnimationManager() {
            return super.getAnimationManager();
        }

        @Override
        public AbstractKeyboardManager getKeyboardManager() {
            return AbstractKeyboardManager.EMPTY_KEYBOARD_MANAGER;
        }

        @Override
        public void addElement(AbstractNode node) {
        }

        @Override
        public void render() {

        }

        @Override
        public void updateTarget(int x, int y) {}

        @Override
        public List<AbstractNode> getAllNodes() {
            return null;
        }
    };
    @Getter
    protected final TimeControl renderTimeControl;
    @Getter
    protected RendererType type;
    @Getter
    protected final WindowSectionNodes windowSectionNodes;
    @Getter
    protected final AbstractTargetManager targetManager;
    @Getter
    protected final AnimationManager animationManager;
    @Getter
    protected final AbstractKeyboardManager keyboardManager;

    public AbstractScene(RendererType type) {
        this.windowSectionNodes = new WindowSectionNodes(new NodeFrameComparator());
        this.renderTimeControl = new TimeControl(new RenderTime());
        this.type = type;
        this.targetManager = new SimpleTargetManager(windowSectionNodes);
        this.animationManager = new AnimationManager(renderTimeControl);
        this.keyboardManager = new SimpleKeyboardManager(targetManager);
    }

    @Override
    public void addElement(AbstractNode node) {
        node.initRenderer(type);
        windowSectionNodes.addNode(node);
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
