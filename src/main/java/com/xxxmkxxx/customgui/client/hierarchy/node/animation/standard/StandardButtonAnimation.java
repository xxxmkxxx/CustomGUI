package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;

public class StandardButtonAnimation extends AbstractAnimation {

    public StandardButtonAnimation(String name, AbstractButton button) {
        super(name);

        addFrame(0, () -> {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xAF3C3B36
            );
        });
    }
}
