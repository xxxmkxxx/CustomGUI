package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
    @Inject(method = "onWindowSizeChanged", at = @At(value = "TAIL"))
    public void onWindowSizeChanged(long window, int width, int height, CallbackInfo ci) {
        EventBus.RESIZE_WINDOW_EVENT.callAllHandlers(this);
    }
}
