package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.node.animations.AbstractAnimation;
import com.xxxmkxxx.customgui.client.hierarchy.node.animations.AnimationFrameTimeStamp;
import com.xxxmkxxx.timecontrol.TimeControl;
import com.xxxmkxxx.timecontrol.common.SimpleTask;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimationManager {
    private final TimeControl timeControl;

    public void addAnimation(AbstractAnimation animation, int amountAnimationCycles) {
        int lastTick = 0;

        for (int i = 0; i < amountAnimationCycles; i++) {
            for (AnimationFrameTimeStamp stamp : animation.getFrames()) {
                scheduleFrame(lastTick, animation.getName(), stamp);
                lastTick += stamp.timeUnit();
            }
        }
    }

    private void scheduleFrame(long tick, String name, AnimationFrameTimeStamp stamp) {
        timeControl.getScheduler().addTask(
                tick,
                new SimpleTask(name, () -> {
                    timeControl.getTickerController().TICKER().removeTask(name);
                    timeControl.getTickerController().createTickerTask(name, () -> stamp.frame().display());
                })
        );
    }
}
