package com.xxxmkxxx.customgui.client.hierarchy.stage.state;

import com.xxxmkxxx.customgui.client.hierarchy.stage.AbstractStage;

public class RenderingStageState implements StageState {
    @Override
    public void next(AbstractStage stage) {

    }

    @Override
    public void prev(AbstractStage stage) {
        stage.setState(StageState.SLEEPING);
    }

    @Override
    public void executeAction(AbstractStage stage) {
        stage.getActiveScene().render();
    }
}
