package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At(value = "HEAD"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        CustomGUIClient.SCREEN_STAGE.getActiveScene().getKeyboardManager().onKeyDown(key);
    }

    @Inject(method = "onChar", at = @At(value = "HEAD"))
    public void onChar(long window, int codePoint, int modifiers, CallbackInfo ci) {
        CustomGUIClient.SCREEN_STAGE.getActiveScene().getKeyboardManager().onCharInput((char) codePoint);
    }
}