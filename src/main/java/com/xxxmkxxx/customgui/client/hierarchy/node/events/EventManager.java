package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.stage.AbstractStage;

public class EventManager {
    public static void sendAction(
            RendererType type, ActionBuilder builder
    ) {
        AbstractStage stage = changeStage(type);
        buildAction(stage, builder);
    }

    private static void buildAction(AbstractStage stage, ActionBuilder builder) {
        builder.build(
                stage.getActiveScene().getTargetManager(),
                stage.getActiveScene().getAnimationManager(),
                stage.getActiveScene().getKeyboardManager()
        ).run();
    }

    private static AbstractStage changeStage(RendererType type) {
        switch (type) {
            case SCREEN: return CustomGUIClient.SCREEN_STAGE;
            case HUD: return CustomGUIClient.HUD_STAGE;
            default: return CustomGUIClient.HUD_STAGE;
        }
    }
}
