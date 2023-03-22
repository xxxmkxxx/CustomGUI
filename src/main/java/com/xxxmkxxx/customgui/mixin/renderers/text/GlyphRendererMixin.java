package com.xxxmkxxx.customgui.mixin.renderers.text;

import com.xxxmkxxx.customgui.client.common.CustomGUIGlyphRenderer;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GlyphRenderer.class)
public abstract class GlyphRendererMixin implements CustomGUIGlyphRenderer {
    @Shadow
    private float minU;
    @Shadow
    private float maxU;
    @Shadow
    private float minV;
    @Shadow
    private float maxV;

    //gag
    @Override
    public void draw(float x, float y, float width, float height, Matrix4f matrix, VertexConsumer vertexConsumer, float red, float green, float blue, float alpha, int light) {
        float finalMinX = x;
        float finalMaxX = x + width + 1.1f;
        float finalMinY = y;
        float finalMaxY = y + height + 0.5f;
        vertexConsumer.vertex(matrix, finalMinX, finalMinY, 0.0f).color(red, green, blue, alpha).texture(minU, minV).light(light).next();
        vertexConsumer.vertex(matrix, finalMinX, finalMaxY, 0.0f).color(red, green, blue, alpha).texture(minU, maxV).light(light).next();
        vertexConsumer.vertex(matrix, finalMaxX, finalMaxY, 0.0f).color(red, green, blue, alpha).texture(maxU, maxV).light(light).next();
        vertexConsumer.vertex(matrix, finalMaxX, finalMinY, 0.0f).color(red, green, blue, alpha).texture(maxU, minV).light(light).next();
    }
}
