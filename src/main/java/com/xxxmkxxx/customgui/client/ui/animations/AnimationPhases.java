package com.xxxmkxxx.customgui.client.ui.animations;

import com.xxxmkxxx.customgui.client.CustomGUIClient;

public class AnimationPhases {
    public static final AnimationPhase EMPTY_PHASE = (node) -> {};

    public static final AnimationPhase PHASE_1 = (node) -> {
        CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                node.getMatrixStack(),
                node.getFrame(),
                -804253680
        );
    };

    public static final AnimationPhase PHASE_2 = (node) -> {
        CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                node.getMatrixStack(),
                node.getFrame(),
                0xFF701f1f
        );
    };

    public static final AnimationPhase PHASE_3 = (node) -> {
        CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                node.getMatrixStack(),
                node.getFrame(),
                0xFF187d3d
        );
    };
}
