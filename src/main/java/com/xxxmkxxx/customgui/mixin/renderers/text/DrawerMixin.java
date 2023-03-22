package com.xxxmkxxx.customgui.mixin.renderers.text;

import com.xxxmkxxx.customgui.client.common.CustomGUIDrawer;
import com.xxxmkxxx.customgui.client.hierarchy.style.Font;
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
    @Shadow float x;
    @Shadow float y;

    @Override
    public boolean accept(FontStorage fontStorage, Font font, int codePoint) {
        GlyphRenderer glyphRenderer = fontStorage.getGlyphRenderer(codePoint);
        Glyph glyph = fontStorage.getGlyph(codePoint);


        if (!(glyphRenderer instanceof EmptyGlyphRenderer)) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(glyphRenderer.getLayer(this.layerType));
            glyphRenderer.draw(x, y, font.getXSizePx(), font.getYSizePx(), matrix, vertexConsumer, red, green, blue, alpha, light);
        }

        float width = (glyph.getAdvance() - 1) * font.getXSizePx() / 8f;

        x += (width + font.getSymbolPaddingPx());

        return true;
    }
}
