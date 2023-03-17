package com.xxxmkxxx.customgui.mixin.renderers.text;

import com.xxxmkxxx.customgui.client.common.CustomGUIDrawer;
import net.minecraft.client.font.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextRenderer.Drawer.class)
public abstract class DrawerMixin implements CharacterVisitor, CustomGUIDrawer {
    @Shadow
    private TextRenderer.TextLayerType layerType;
    @Shadow
    VertexConsumerProvider vertexConsumers;
    @Shadow
    private Matrix4f matrix;
    @Shadow
    private float red;
    @Shadow
    private float green;
    @Shadow
    private float blue;
    @Shadow
    private float alpha;
    @Shadow
    private int light;
    @Shadow private float x;
    @Shadow private float y;

    @Override
    public boolean accept(FontStorage fontStorage, float symbolWidth, float symbolHeight, float indent, int codePoint) {
        GlyphRenderer glyphRenderer = fontStorage.getGlyphRenderer(codePoint);

        if (!(glyphRenderer instanceof EmptyGlyphRenderer)) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(glyphRenderer.getLayer(this.layerType));
            glyphRenderer.draw(x, y, symbolWidth, symbolHeight, matrix, vertexConsumer, red, green, blue, alpha, light);
        }

        //gag
        float symbolIndent = symbolWidth + indent;

        x += symbolIndent;

        return true;
    }
}
