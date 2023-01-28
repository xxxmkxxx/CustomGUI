package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.CustomGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void render(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (tick && client.world != null) {
            CustomGUI.getInstance().getHudStage().render();
            CustomGUI.getInstance().getScreenStage().render();
        }
    }
}
