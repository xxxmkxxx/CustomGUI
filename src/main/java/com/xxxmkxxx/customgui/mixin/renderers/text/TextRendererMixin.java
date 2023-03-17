package com.xxxmkxxx.customgui.mixin.renderers.text;

import com.xxxmkxxx.customgui.client.common.CustomGUIDrawer;
import com.xxxmkxxx.customgui.client.common.CustomGUITextRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.style.Font;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.font.TextRenderer.Drawer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin implements CustomGUITextRenderer {
    @Shadow
    abstract FontStorage getFontStorage(Identifier id);

    @Override
    public int drawInternal(float x, float y, OrderedText text, Font font, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, boolean seeThrough, int backgroundColor, int light) {
        Drawer drawer = ((TextRenderer)(Object)this).new Drawer(vertexConsumerProvider, x, y, font.getHexColor(), false, matrix, seeThrough, light);
        FontStorage fontStorage = getFontStorage(Style.DEFAULT_FONT_ID);
        text.accept((index, style, codePoint) -> ((CustomGUIDrawer) drawer).accept(fontStorage, font.getXSizePx(), font.getYSizePx(), font.getSymbolPaddingPx(), codePoint));
        return 0;
    }
}
