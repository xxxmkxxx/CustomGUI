package com.xxxmkxxx.customgui.client.common;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix4f;

public interface CustomGUIGlyphRenderer {
    default void draw(float x, float y, float width, float height, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int light) {}
}
