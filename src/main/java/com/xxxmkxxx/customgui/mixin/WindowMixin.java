package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.client.common.MinecraftOptions;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
    @Shadow private int height;

    @Shadow private int width;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void init(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
        MinecraftOptions.windowHeight = height;
        MinecraftOptions.windowWidth = width;
    }
}
