package com.xxxmkxxx.customgui.client.common;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Matrix4f;

public interface CustomGUITextRenderer {
    default int drawInternal(
            OrderedText text, int textSize,
            float x, float y,
            float width, float height,
            int color, Matrix4f matrix,
            VertexConsumerProvider vertexConsumerProvider,
            boolean seeThrough, int backgroundColor, int light
    ) {
        return 0;
    }
}
