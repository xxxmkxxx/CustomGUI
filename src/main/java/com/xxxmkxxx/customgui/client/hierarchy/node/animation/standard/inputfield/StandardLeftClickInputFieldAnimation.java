package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.inputfield;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.field.AbstractField;

public class StandardLeftClickInputFieldAnimation extends AbstractAnimation<AbstractField> {
    public StandardLeftClickInputFieldAnimation() {
        long timeUnit = 0;
        addFrame(timeUnit+=10, field -> {});
        addFrame(timeUnit+=20, field -> {
            CustomGUIClient.NODE_DRAWABLE_HELPER.drawVerticalLine(
                    field.getMatrixStack(),
                    field.getFrame().getStartPos(),
                    field.getFrame().getStopPos(),
                    0xFFf50707
            );
        });
        addFrame(timeUnit+=10, field -> {});
    }
}
