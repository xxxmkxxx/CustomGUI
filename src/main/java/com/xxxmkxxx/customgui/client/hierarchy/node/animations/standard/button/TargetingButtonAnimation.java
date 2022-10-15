package com.xxxmkxxx.customgui.client.hierarchy.node.animations.standard.button;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.node.animations.AbstractAnimation;
import com.xxxmkxxx.customgui.client.hierarchy.node.animations.AnimationFrame;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;

public class TargetingButtonAnimation extends AbstractAnimation {
    public TargetingButtonAnimation(AbstractButton button) {
        super("standardTargetingButton-" + button.getName().getString(), 1);

        addFrame(1, () -> {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xFF331e1e
            );
        });

        addFrame(2, () -> {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xFF1e8583
            );
        });

        addFrame(3, () -> {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xFF6515b0
            );
        });

        addFrame(4, AnimationFrame.EMPTY);
    }
}
