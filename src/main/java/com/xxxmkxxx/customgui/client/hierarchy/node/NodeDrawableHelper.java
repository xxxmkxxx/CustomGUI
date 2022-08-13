package com.xxxmkxxx.customgui.client.hierarchy.node;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class NodeDrawableHelper extends DrawableHelper {
    public void gradient(MatrixStack matrices, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
        fillGradient(matrices, startX, startY, endX, endY, colorStart, colorEnd);
    }
}
