package com.xxxmkxxx.customgui.client.common.util;

import com.xxxmkxxx.customgui.client.hierarchy.style.Font;
import com.xxxmkxxx.customgui.mixin.renderers.text.TextRendererMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Utils {
    public static float getTextWidth(String text, Font font) {
        return MinecraftClient.getInstance().textRenderer.getTextWidth(text, font);
    }
    public static int nonNullIntValue(int value) {
        return value > 0 ? value : 1;
    }

    public static int nonNullIntValue(double value) {
        return nonNullIntValue((int) Math.ceil(value));
    }

    public static int nonNullIntValue(float value) {
        return nonNullIntValue((int) Math.ceil(value));
    }
}
