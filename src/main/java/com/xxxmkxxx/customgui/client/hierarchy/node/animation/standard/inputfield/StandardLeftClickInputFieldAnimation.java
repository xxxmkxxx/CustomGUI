package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.inputfield;

import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.field.NoneExpandableInputField;

public class StandardLeftClickInputFieldAnimation extends AbstractAnimation<NoneExpandableInputField> {
    public StandardLeftClickInputFieldAnimation() {
        long timeUnit = 0;
        addFrame(timeUnit+=10, field -> {});
        addFrame(timeUnit+=1, field -> field.getInputCursor().display());
        addFrame(timeUnit+=1, field -> {});
        addFrame(timeUnit+=40, field -> field.getInputCursor().hide());
        addFrame(timeUnit+=1, field -> {});
    }
}
