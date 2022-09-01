package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;

public class ScreenStage extends AbstractStage {
    public ScreenStage() {
        super(RendererType.SCREEN);
        state = StageState.SLEEPING;
    }

    @Override
    public void render() {
        state.executeAction(this);
    }

    public void onCursorUpdate(int x, int y) {
        activeScene.updateTarget(x, y);
    }
}
