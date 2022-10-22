package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.hierarchy.node.NodeDrawableHelper;
import com.xxxmkxxx.customgui.client.hierarchy.stage.HudStage;
import com.xxxmkxxx.customgui.client.hierarchy.stage.ScreenStage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class CustomGUIClient implements ClientModInitializer {
    public static final NodeDrawableHelper NODE_DRAWABLE_HELPER = new NodeDrawableHelper();
    public static final HudStage HUD_STAGE = new HudStage();
    public static final ScreenStage SCREEN_STAGE = new ScreenStage();

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            TestHud.render();
        });
    }
}
