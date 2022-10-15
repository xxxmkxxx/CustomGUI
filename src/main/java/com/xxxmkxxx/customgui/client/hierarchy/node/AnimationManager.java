package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.node.animations.AbstractAnimation;
import com.xxxmkxxx.customgui.client.hierarchy.node.animations.AnimationFrameTimeStamp;
import com.xxxmkxxx.timecontrol.TimeControl;
import com.xxxmkxxx.timecontrol.common.SimpleTask;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimationManager {
    private final TimeControl timeControl;

    public void addAnimation(AbstractAnimation animation) {
        for (AnimationFrameTimeStamp stamp : animation.getFrames()) {
            timeControl.getScheduler().addTask(
                    stamp.timeUnit(),
                    new SimpleTask(animation.getName(), () -> {
                        timeControl.getTickerController().TICKER().removeTask(animation.getName());
                        timeControl.getTickerController().createTickerTask(animation.getName(), () -> stamp.frame().display());
                    })
            );
        }

    }
}
