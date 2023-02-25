package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;

public class ScreenStage extends AbstractStage {
    public ScreenStage(int width, int height) {
        super(RendererType.SCREEN, width, height);
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
