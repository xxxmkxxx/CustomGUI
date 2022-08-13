package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.common.Frame;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class NodeDrawableHelper extends DrawableHelper {
    public static int startGradientColor = -1072689136;
    public static int stopGradientColor = -804253680;

    public void gradientFillFrame(MatrixStack matrices, int startX, int startY, int stopX, int stopY, int colorStart, int colorEnd) {
        fillGradient(matrices, startX, startY, stopX, stopY, colorStart, colorEnd);
    }

    public void gradientFillFrame(MatrixStack matrices, int startX, int startY, int stopX, int stopY) {
        fillGradient(matrices, startX, startY, stopX, stopY, startGradientColor, stopGradientColor);
    }

    public void gradientFillFrame(MatrixStack matrices, Frame frame, int colorStart, int colorEnd) {
        gradientFillFrame(
                matrices,
                frame.getStartPos().x(), frame.getStartPos().y(),
                frame.getStopPos().x(), frame.getStopPos().y(),
                colorStart, colorEnd
        );
    }

    public void fillFrame(MatrixStack matrices, int startX, int startY, int stopX, int stopY, int color) {
        fill(matrices, startX, startY, stopX, stopY, color);
    }

    public void fillFrame(MatrixStack matrices, Frame frame, int color) {
        fillFrame(
                matrices,
                frame.getStartPos().x(), frame.getStartPos().y(),
                frame.getStopPos().x(), frame.getStopPos().y(),
                color
        );
    }
}
