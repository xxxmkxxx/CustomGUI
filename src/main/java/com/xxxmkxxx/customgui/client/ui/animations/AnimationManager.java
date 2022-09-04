package com.xxxmkxxx.customgui.client.ui.animations;

import java.util.LinkedList;
import java.util.List;

public class AnimationManager {
    private static long COUNTER;
    private static final List<AbstractAnimation> ANIMATIONS = new LinkedList<>();

    public static void addAnimation(AbstractAnimation animation) {
        ANIMATIONS.add(animation);
    }

    public static void render() {
        ANIMATIONS.forEach(animation -> {
            if (COUNTER++ % animation.getFrameRate() == 0)
                animation.changePhase();

            animation.displayPhase();
        });
    }
}
