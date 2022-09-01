package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow private double x;

    @Shadow private double y;

    @Inject(method = "lockCursor", at = @At(value = "HEAD"), cancellable = true)
    public void lockCursor(CallbackInfo ci) {
        if (CustomGUIClient.SCREEN_STAGE.getState() == StageState.RENDERING)
            ci.cancel();
    }

    @Inject(method = "onCursorPos", at = @At(value = "TAIL"))
    public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
        CustomGUIClient.SCREEN_STAGE.onCursorUpdate((int) x/2, (int) y/2);
    }
}
