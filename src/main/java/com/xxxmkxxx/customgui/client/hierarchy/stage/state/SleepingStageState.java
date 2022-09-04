package com.xxxmkxxx.customgui.client.hierarchy.stage.state;

import com.xxxmkxxx.customgui.client.hierarchy.stage.AbstractStage;

public class SleepingStageState implements StageState {
    @Override
    public void next(AbstractStage stage) {
        stage.setState(StageState.RENDERING);
    }

    @Override
    public void prev(AbstractStage stage) {

    }

    @Override
    public void executeAction(AbstractStage stage) {
    }
}
