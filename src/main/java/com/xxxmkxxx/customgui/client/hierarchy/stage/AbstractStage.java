package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSectionNodes;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractStage implements Stage {
    protected final RendererType type;
    @Getter
    private final Window window;
    @Getter
    protected AbstractScene activeScene;
    @Getter @Setter
    protected StageState state;

    public AbstractStage(RendererType type, int width, int height, int scaledWidth, int scaledHeight) {
        this.type = type;
        this.window = new Window(new WindowSectionNodes(), width, height, scaledWidth, scaledHeight);
        EventBus.RESIZE_WINDOW_EVENT.addHandler(window, window);
    }

    @Override
    public void setActiveScene(AbstractScene scene) {
        Validator.checkNullObject(scene);
        window.setWindowSectionNodes(scene.getWindowSectionNodes());
        activeScene = scene;
        state.next(this);
    }

    @Override
    public void clearScene() {
        activeScene = null;
        state.prev(this);
    }
}
