package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character.KeyboardCharInputEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import com.xxxmkxxx.customgui.client.hierarchy.stage.state.StageState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow public abstract boolean wasLeftButtonClicked();

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract boolean wasRightButtonClicked();

    @Inject(method = "lockCursor", at = @At(value = "HEAD"), cancellable = true)
    public void lockCursor(CallbackInfo ci) {
        if (CustomGUIClient.SCREEN_STAGE.getState() == StageState.RENDERING)
            ci.cancel();
    }

    @Inject(method = "onCursorPos", at = @At(value = "TAIL"))
    public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
        int xPos = (int) x / 2;
        int yPos = (int) y / 2;

        if (client.getWindow().isFullscreen()) {
            xPos = (int) x / 4;
            yPos = (int) y / 4;
        }

        CustomGUIClient.SCREEN_STAGE.onCursorUpdate(xPos, yPos);
    }

    @Inject(method = "onMouseButton", at = @At(value = "TAIL"))
    public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        AbstractScene scene = CustomGUIClient.SCREEN_STAGE.getActiveScene();
        AbstractNode node = scene.getTargetManager().getCurrentTarget();

        if (wasLeftButtonClicked()) {
            if (node instanceof LeftClickEventHandler handler) {
                handler.onLeftClick();
            }
        }

        if (wasRightButtonClicked()) {}
    }
}
