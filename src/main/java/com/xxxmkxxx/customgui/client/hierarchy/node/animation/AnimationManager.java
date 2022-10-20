package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.timecontrol.TimeControl;
import com.xxxmkxxx.timecontrol.common.SimpleTask;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnimationManager {
    private final TimeControl timeControl;

    public void addSimpleAnimation(AbstractAnimation animation, int amountAnimationCycles) {
        for (int i = 0; i < amountAnimationCycles; i++) {
            addAnimation(animation, animation.getFrames().size());
        }
    }

    public void addStickyAnimation(AbstractAnimation animation, int indexStickyFrame) {
        addAnimation(animation, indexStickyFrame);
    }

    public void deleteStickyAnimation(AbstractAnimation animation) {
        timeControl.getTicker().removeTask(animation.getName());
    }

    private void scheduleFrame(long tick, String name, AnimationFrame frame) {
        timeControl.getScheduler().addTask(
                tick,
                new SimpleTask(name, () -> {
                    timeControl.getTickerController().TICKER().removeTask(name);
                    timeControl.getTickerController().createTickerTask(name, () -> frame.display());
                })
        );
    }

    private void clearFrame(long tick, String animationName) {
        timeControl.getScheduler().addTask(
                tick,
                new SimpleTask(
                        animationName,
                        () -> timeControl.getTickerController().TICKER().removeTask(animationName)
                )
        );
    }

    private void addAnimation(AbstractAnimation animation, int amountAnimationFrames) {
        int lastTick = 0;

        for (int i = 0; i <= amountAnimationFrames; i++) {
            AnimationFrameTimeStamp stamp = animation.getFrames().get(i);
            scheduleFrame(lastTick += stamp.timeUnit(), animation.getName(), stamp.frame());
        }

        clearFrame(lastTick, animation.getName());
    }
}
