package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.inputfield;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.field.AbstractField;
import com.xxxmkxxx.customgui.client.ui.controls.field.InputField;

public class StandardLeftClickInputFieldAnimation extends AbstractAnimation<InputField> {
    public StandardLeftClickInputFieldAnimation() {
        long timeUnit = 0;
        addFrame(timeUnit+=10, field -> {});
        addFrame(timeUnit+=20, field -> {
            CustomGUIClient.NODE_DRAWABLE_HELPER.drawVerticalLine(
                    field.getMatrixStack(),
                    field.getInputCursor().getFrame().getStartPos(),
                    field.getInputCursor().getFrame().getStopPos(),
                    field.getInputCursor().getColor()
            );
        });
        addFrame(timeUnit+=10, field -> {});
    }
}
