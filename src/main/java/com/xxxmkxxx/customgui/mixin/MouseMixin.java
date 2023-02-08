package com.xxxmkxxx.customgui.mixin;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
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
        if (CustomGUI.getInstance().getScreenStage().getState() == StageState.RENDERING) ci.cancel();
    }

    @Inject(method = "onCursorPos", at = @At(value = "TAIL"))
    public void onCursorPos(long window, double x, double y, CallbackInfo ci) {
        int xPos = (int) (x * this.client.getWindow().getScaledWidth() / this.client.getWindow().getWidth());
        int yPos = (int) (y * this.client.getWindow().getScaledHeight() / this.client.getWindow().getHeight());

        EventBus.MOVE_EVENT.callAllHandlers(x, y);
        CustomGUI.getInstance().getScreenStage().onCursorUpdate(xPos, yPos);
    }

    @Inject(method = "onMouseButton", at = @At(value = "TAIL"))
    public void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        AbstractScene scene = CustomGUI.getInstance().getScreenStage().getActiveScene();
        AbstractNode node = scene.getTargetManager().getCurrentTarget();

        if (wasLeftButtonClicked()) {
            if (node instanceof LeftClickEventHandler handler) {
                handler.onLeftClick();
            }
        }

        if (wasRightButtonClicked()) {}
    }
}
