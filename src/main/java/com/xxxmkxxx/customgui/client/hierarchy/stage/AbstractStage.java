package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;

public abstract class AbstractStage implements Stage {
    protected final RendererType type;
    @Getter
    private final Window window;
    @Getter
    protected AbstractScene activeScene;
    @Getter @Setter
    protected StageState state;

    public AbstractStage(RendererType type) {
        this.type = type;
        this.window = new Window();
    }

    @Override
    public void setActiveScene(AbstractScene scene) {
        Validator.checkNullObject(scene);
        window.setNodes(scene.getAllNodes());
        activeScene = scene;
        state.next(this);
    }

    @Override
    public void clearScene() {
        activeScene = null;
        state.prev(this);
    }

    @Override
    public void init() {
        initWindow();
    }

    private void initWindow() {
        net.minecraft.client.util.Window win = MinecraftClient.getInstance().getWindow();

        window.setWindowWidth(win.getWidth());
        window.setWindowHeight(win.getHeight());
        window.setScaledWindowWidth(win.getScaledWidth());
        window.setScaledWindowHeight(win.getScaledHeight());

        EventBus.RESIZE_WINDOW_EVENT.addHandler(window, window);
    }
}
