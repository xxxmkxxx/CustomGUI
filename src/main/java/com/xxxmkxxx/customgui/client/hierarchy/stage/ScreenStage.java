package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import lombok.Getter;

public class ScreenStage implements Stage {
    private final RendererType type = RendererType.SCREEN;

    @Getter
    private AbstractScene scene;

    public void setScene(AbstractScene scene) {
        if (scene.getType() == type)
            this.scene = scene;
    }

    @Override
    public void render() {
        scene.render();
    }
}
