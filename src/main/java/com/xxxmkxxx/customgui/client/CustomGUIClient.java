package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.hierarchy.stage.HudStage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class CustomGUIClient implements ClientModInitializer {
    public static final HudStage HUD_STAGE = new HudStage();

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            HUD_STAGE.render();
        });

        TestHud.render();
    }
}
