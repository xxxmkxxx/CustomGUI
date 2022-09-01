package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class AbstractStage implements Stage {
    protected final RendererType type;
    @Getter
    protected AbstractScene activeScene;
    @Getter @Setter
    protected StageState state;

    @Override
    public void setActiveScene(AbstractScene scene) {
        Validator.checkNullObject(scene);
        this.activeScene = scene;
        state.next(this);
    }

    @Override
    public void clearScene() {
        activeScene = null;
        state.prev(this);
    }
}
