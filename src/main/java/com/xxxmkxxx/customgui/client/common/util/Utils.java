package com.xxxmkxxx.customgui.client.common.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Utils {
    public static int getTextHeight() {
        return MinecraftClient.getInstance().textRenderer.fontHeight;
    }
    public static int getTextWidth(Text text) {
        return MinecraftClient.getInstance().textRenderer.getWidth(text);
    }
    public static int nonNullIntValue(int value) {
        return value > 0 ? value : 1;
    }

    public static int nonNullIntValue(double value) {
        return nonNullIntValue((int) Math.ceil(value));
    }
}
