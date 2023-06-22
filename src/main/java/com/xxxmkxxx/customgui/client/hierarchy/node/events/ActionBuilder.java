package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.keyboard.AbstractKeyboardManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.AbstractTargetManager;

import java.util.LinkedList;
import java.util.Queue;

public class ActionBuilder {
    private AbstractTargetManager targetManager;
    private AbstractAnimationManager animationManager;
    private AbstractKeyboardManager keyboardManager;
    private Queue<Runnable> actions = new LinkedList<>();

    public ActionBuilder blockKeyboard() {
        actions.add(() -> keyboardManager.setActive(false));
        return this;
    }

    public ActionBuilder unblockKeyboard() {
        actions.add(() -> keyboardManager.setActive(true));
        return this;
    }

    public ActionBuilder activeNode(AbstractNode node) {
        actions.add(() -> targetManager.setActiveNode(node));
        return this;
    }

    public <N extends AbstractNode> ActionBuilder addSimpleAnimation(String animationName, AbstractAnimation<N> animation, N node, int amountAnimationCycles) {
        actions.add(() -> {
            animationManager.addSimpleAnimation(node, animationName, animation, amountAnimationCycles);
        });
        return this;
    }

    public <N extends AbstractNode> ActionBuilder addStickyAnimation(String animationName, AbstractAnimation<N> animation, N node, int stickyAnimationFrameIndex) {
        actions.add(() -> {
            animationManager.addStickyAnimation(node, animationName, animation, stickyAnimationFrameIndex);
        });
        return this;
    }

    public <N extends AbstractNode> ActionBuilder addCyclicAnimation(String animationName, AbstractAnimation<N> animation, N node) {
        actions.add(() -> {
            animationManager.addCyclicAnimation(node, animationName, animation);
        });
        return this;
    }

    public ActionBuilder deleteStickyAnimation(String animationName) {
        actions.add(() -> {
            animationManager.deleteStickyAnimation(animationName);
        });
        return this;
    }

    public <N extends AbstractNode> ActionBuilder deleteCyclicAnimation(String animationName) {
        actions.add(() -> {
            animationManager.deleteCyclicAnimation(animationName);
        });
        return this;
    }

    public Runnable build(AbstractTargetManager targetManager, AbstractAnimationManager animationManager, AbstractKeyboardManager keyboardManager) {
        this.targetManager = targetManager;
        this.animationManager = animationManager;
        this.keyboardManager = keyboardManager;

        return () -> {
            actions.forEach(Runnable::run);
        };
    }

    public static ActionBuilder of() {
        return new ActionBuilder();
    }
}
