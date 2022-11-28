package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.KeyboardManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AnimationManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;

import java.util.LinkedList;
import java.util.Queue;

public class ActionBuilder {
    private TargetManager targetManager;
    private AnimationManager animationManager;
    private KeyboardManager keyboardManager;
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

    public Runnable build(TargetManager targetManager, AnimationManager animationManager, KeyboardManager keyboardManager) {
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
