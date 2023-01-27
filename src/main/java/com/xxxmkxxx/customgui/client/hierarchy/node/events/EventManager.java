package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.common.Config;
import com.xxxmkxxx.customgui.client.common.Register;
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
            case SCREEN: return Register.getGUI(Config.getGuiName()).getScreenStage();
            case HUD: return Register.getGUI(Config.getGuiName()).getHudStage();
            default: return Register.getGUI(Config.getGuiName()).getHudStage();
        }
    }
}
