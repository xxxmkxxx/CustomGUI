package com.xxxmkxxx.customgui.client.ui.containers.pane;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

@FunctionalInterface
public interface PaneState {
    void execute(MatrixStack matrixStack, int x1, int y1, int x2, int y2, int color);

    PaneState VIEWED = DrawableHelper::fill;
    PaneState HIDED = (matrixStack, x1, y1, x2, y2, color) -> {};
}
