package com.xxxmkxxx.customgui.client.ui.animations.button;

import com.xxxmkxxx.customgui.client.ui.animations.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.animations.AnimationPhases;
import com.xxxmkxxx.customgui.client.ui.animations.PermanentAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;

@SuppressWarnings("unused")
public class ButtonAnimations {
    public final AbstractAnimation SIMPLE;

    public ButtonAnimations(AbstractButton button) {
        SIMPLE = new PermanentAnimation(button, AnimationPhases.PHASE_1, AnimationPhases.PHASE_2, AnimationPhases.PHASE_3);
        SIMPLE.setFrameRate(2);
    }
}