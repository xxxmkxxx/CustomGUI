package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.timecontrol.TimeControl;
import com.xxxmkxxx.timecontrol.common.SimpleTask;
import com.xxxmkxxx.timecontrol.common.Task;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AnimationManager {
    private final TimeControl timeControl;
    private final Map<String, Runnable> cyclicTasks = new HashMap<>();

    public <N extends AbstractNode> void addSimpleAnimation(N node, String animationName, AbstractAnimation<N> animation, int amountAnimationCycles) {
        long startTick = 0;

        for (int i = 0; i < amountAnimationCycles; i++) {
            startTick = addAnimation(node, animationName, animation, animation.getFrames().size(), startTick);
        }

        clearFrame(startTick, animationName);
    }

    public <N extends AbstractNode> void addStickyAnimation(N node, String animationName, AbstractAnimation<N> animation, int indexStickyFrame) {
        addAnimation(node, animationName, animation, indexStickyFrame, 0);
    }

    public void deleteStickyAnimation(String animationName) {
        timeControl.getTicker().removeTask(animationName);
    }

    public <N extends AbstractNode> void addCyclicAnimation(N node, String animationName, AbstractAnimation<N> animation) {
        cyclicTasks.put(
                animationName,
                () -> {
                    addAnimationWithLastTask(node, animationName, animation, () -> cyclicTasks.get(animationName).run());
                }
        );

        addAnimationWithLastTask(node, animationName, animation, () -> cyclicTasks.get(animationName).run());
    }

    public void deleteCyclicAnimation(String animationName) {
        cyclicTasks.replace(animationName, () -> {});
    }

    private <N extends AbstractNode> void scheduleFrame(long tick, String animationName, AnimationFrame<N> frame, N node) {
        timeControl.getScheduler().addTask(
                tick,
                new SimpleTask(animationName, () -> {
                    timeControl.getTickerController().TICKER().removeTask(animationName);
                    timeControl.getTickerController().createTickerTask(animationName, () -> frame.display(node));
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

    private <N extends AbstractNode> long addAnimation(
            N node, String animationName, AbstractAnimation<N> animation,
            int amountAnimationFrames, long startTick
    ) {
        for (int i = 0; i < amountAnimationFrames; i++) {
            AnimationFrameTimeStamp<N> animationFrameTimeStamp = animation.getFrames().get(i);

            scheduleFrame(
                    startTick + animationFrameTimeStamp.timeUnit(),
                    animationName,
                    animationFrameTimeStamp.frame(),
                    node
            );
        }

        return startTick + animation.getFrames().get(amountAnimationFrames - 1).timeUnit();
    }

    private <N extends AbstractNode> void addAnimationWithLastTask(N node, String animationName, AbstractAnimation<N> animation, Runnable runnable) {
        long timeUnit = addAnimation(node, animationName, animation, animation.getFrames().size(), 0);

        timeControl.getSchedulerController().scheduleTask(
                timeUnit + 1,
                new SimpleTask(animationName, runnable)
        );
    }
}
