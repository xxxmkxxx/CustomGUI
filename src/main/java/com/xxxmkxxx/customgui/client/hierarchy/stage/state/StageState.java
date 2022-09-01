package com.xxxmkxxx.customgui.client.hierarchy.stage.state;

import com.xxxmkxxx.customgui.client.hierarchy.stage.AbstractStage;

public interface StageState {
    void next(AbstractStage stage);
    void prev(AbstractStage stage);
    void executeAction(AbstractStage stage);

    StageState SLEEPING = new SleepingStageState();
    StageState RENDERING = new RenderingStageState();
}
