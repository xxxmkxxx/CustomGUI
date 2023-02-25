package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;

public class HudStage extends AbstractStage {
    public HudStage(int width, int height, int scaledWidth, int scaledHeight) {
        super(RendererType.HUD, width, height);
        state = StageState.SLEEPING;
    }

    @Override
    public void render() {
        state.executeAction(this);
    }
}
