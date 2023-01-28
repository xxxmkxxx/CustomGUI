package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.initializer.Initializers;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.Section;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final private Window window;

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    public void init(RunArgs args, CallbackInfo ci) {
        Section.updateFrames(window.getWidth(), window.getHeight());
        CustomGUI.CustomGUIInitializer.init(window);
    }

    @Inject(at = @At(value = "TAIL"), method = "tick")
    private void tick(CallbackInfo ci) {
        Initializers.CUSTOM_GUI_INITIALIZER.initAll(window);
    }
}
