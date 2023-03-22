package com.xxxmkxxx.customgui.client.common;

import com.xxxmkxxx.customgui.client.hierarchy.style.Font;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Matrix4f;

public interface CustomGUITextRenderer {
    default int drawInternal(
            float x, float y,
            OrderedText text, Font font,
            Matrix4f matrix,
            VertexConsumerProvider vertexConsumerProvider,
            boolean seeThrough, int backgroundColor, int light
    ) {
        return 0;
    }

    default float getSymbolWidth(int codepoint, Font font) {
        return 0;
    }

    default float getTextWidth(String text, Font font) {
        return 0;
    }
}
