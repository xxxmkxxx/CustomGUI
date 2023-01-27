package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.button;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;

public class StandardLeftClickButtonAnimation extends AbstractAnimation<AbstractButton> {
    public StandardLeftClickButtonAnimation() {
        addFrame(0, button -> {
            CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xAF3C3B36
            );
        });
        addFrame(1, button -> {
            CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xAFbf1111
            );
        });
    }
}
