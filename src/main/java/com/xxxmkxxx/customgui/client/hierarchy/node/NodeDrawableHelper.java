package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.style.Font;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class NodeDrawableHelper extends DrawableHelper {
    public static int startGradientColor = -1072689136;
    public static int stopGradientColor = -804253680;
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    private void fill(Matrix4f matrix, float startX, float startY, float stopX, float stopY, int color) {
        float index;
        if (startX < stopX) {
            index = startX;
            startX = stopX;
            stopX = index;
        }
        if (startY < stopY) {
            index = startY;
            startY = stopY;
            stopY = index;
        }
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float g = (float)(color >> 16 & 0xFF) / 255.0f;
        float h = (float)(color >> 8 & 0xFF) / 255.0f;
        float j = (float)(color & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, startX, stopY, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, stopX, stopY, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, stopX, startY, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, startX, startY, 0.0f).color(g, h, j, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void fillGradient(MatrixStack matrices, float startX, float startY, float endX, float endY, int colorStart, int colorEnd, int z) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        fillGradient(matrices.peek().getPositionMatrix(), bufferBuilder, startX, startY, endX, endY, z, colorStart, colorEnd);
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    private void fillGradient(Matrix4f matrix, BufferBuilder builder, float startX, float startY, float stopX, float stopY, float z, int colorStart, int colorEnd) {
        float f = (float)(colorStart >> 24 & 0xFF) / 255.0f;
        float g = (float)(colorStart >> 16 & 0xFF) / 255.0f;
        float h = (float)(colorStart >> 8 & 0xFF) / 255.0f;
        float i = (float)(colorStart & 0xFF) / 255.0f;
        float j = (float)(colorEnd >> 24 & 0xFF) / 255.0f;
        float k = (float)(colorEnd >> 16 & 0xFF) / 255.0f;
        float l = (float)(colorEnd >> 8 & 0xFF) / 255.0f;
        float m = (float)(colorEnd & 0xFF) / 255.0f;
        builder.vertex(matrix, stopX, startY, z).color(g, h, i, f).next();
        builder.vertex(matrix, startX, startY, z).color(g, h, i, f).next();
        builder.vertex(matrix, startX, stopY, z).color(k, l, m, j).next();
        builder.vertex(matrix, stopX, stopY, z).color(k, l, m, j).next();
    }

    public void gradientFillFrame(MatrixStack matrix, float startX, float startY, float stopX, float stopY, int colorStart, int colorEnd) {
        fillGradient(matrix, startX, startY, stopX, stopY, colorStart, colorEnd, 0);
    }

    public void gradientFillFrame(MatrixStack matrix, float startX, float startY, float stopX, float stopY) {
        fillGradient(matrix, startX, startY, stopX, stopY, startGradientColor, stopGradientColor, 0);
    }

    public void gradientFillFrame(MatrixStack matrix, AbstractFrame frame, int colorStart, int colorEnd) {
        gradientFillFrame(
                matrix,
                frame.getStartPos().getX(), frame.getStartPos().getY(),
                frame.getStopPos().getX(), frame.getStopPos().getY(),
                colorStart, colorEnd
        );
    }

    public void fillFrame(MatrixStack matrix, float startX, float startY, float stopX, float stopY, int color) {
        fill(matrix.peek().getPositionMatrix(), startX, startY, stopX, stopY, color);
    }

    public void fillFrame(MatrixStack matrix, AbstractFrame frame, int color) {
        fillFrame(
                matrix,
                frame.getStartPos().getX(), frame.getStartPos().getY(),
                frame.getStopPos().getX(), frame.getStopPos().getY(),
                color
        );
    }

    @Deprecated
    public void fillRoundingFrame() {
        Matrix4f m = new MatrixStack().peek().getPositionMatrix();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(m, 0, 0, 0).color(stopGradientColor).next();
        bufferBuilder.vertex(m, 50, 50, 0).color(stopGradientColor).next();
        bufferBuilder.vertex(m, 0, 10, 0).color(stopGradientColor).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
    }

    public void drawText(MatrixStack matrix, Text text, float x, float y, int color) {
        CLIENT.textRenderer.draw(matrix, text, x, y, color);
    }

    public void drawText(MatrixStack matrix, Text text, Pos pos, int color) {
        drawText(matrix, text, pos.getX(), pos.getY(), color);
    }

    public void drawText(MatrixStack matrix, Text text, float x, float y, Font font) {
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());

        CLIENT.textRenderer.drawInternal(
                x, y,
                text.asOrderedText(), font,
                matrix.peek().getPositionMatrix(),
                immediate, false,
                0, LightmapTextureManager.MAX_LIGHT_COORDINATE
        );

        immediate.draw();
    }

    public void drawTexture(ItemStack stack, AbstractFrame frame) {
        float width = frame.getStopPos().getX() - frame.getStartPos().getX();
        float height = frame.getStopPos().getY() - frame.getStartPos().getY();
        drawTexture(stack, frame.getStartPos(), width, height);
    }

    public void drawTexture(ItemStack stack, Pos pos, float width, float height) {
        final ItemRenderer itemRenderer = CLIENT.getItemRenderer();
        BakedModel bakedModel = itemRenderer.getModel(stack, null, CLIENT.player, 0);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(pos.getX(), pos.getY(), 0);
        matrixStack.translate(width / 2.0D, height / 2.0D, 0);
        matrixStack.scale(1.0F, -1.0F, 1.0F);
        matrixStack.scale(width, height, 0);

        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        boolean bl = !bakedModel.isSideLit();
        if (bl) {
            DiffuseLighting.disableGuiDepthLighting();
        }

        itemRenderer.renderItem(stack, ModelTransformation.Mode.GUI, false, matrixStack2, immediate, 15728880, OverlayTexture.DEFAULT_UV, bakedModel);

        immediate.draw();
        RenderSystem.enableDepthTest();
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();

        if (bl) {
            DiffuseLighting.enableGuiDepthLighting();
        }
    }

    public void drawTexture(MatrixStack matrix, AbstractFrame frame, Identifier texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, texture);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder
                .vertex(matrix.peek().getPositionMatrix(), frame.getStartPos().getX(), frame.getStopPos().getY(), 0)
                .texture(0, 1)
                .next();
        bufferBuilder
                .vertex(matrix.peek().getPositionMatrix(), frame.getStopPos().getX(), frame.getStopPos().getY(), 0)
                .texture(1, 1)
                .next();
        bufferBuilder
                .vertex(matrix.peek().getPositionMatrix(), frame.getStopPos().getX(), frame.getStartPos().getY(), 0)
                .texture(1, 0)
                .next();
        bufferBuilder
                .vertex(matrix.peek().getPositionMatrix(), frame.getStartPos().getX(), frame.getStartPos().getY(), 0)
                .texture(0, 0)
                .next();
        bufferBuilder.end();

        BufferRenderer.draw(bufferBuilder);
    }

    public void drawFrameAroundFrame(MatrixStack matrix, AbstractFrame frame, int color) {
        //Top line
        drawHorizontalLine(
                matrix,
                Utils.nonNullIntValue(frame.getStartPos().getX()),
                Utils.nonNullIntValue(frame.getStopPos().getX()),
                Utils.nonNullIntValue(frame.getStartPos().getY()),
                color
        );
        //Down line
        drawHorizontalLine(
                matrix,
                Utils.nonNullIntValue(frame.getStartPos().getX()),
                Utils.nonNullIntValue(frame.getStopPos().getX()),
                Utils.nonNullIntValue(frame.getStopPos().getY()),
                color
        );
        //Left line
        drawVerticalLine(
                matrix,
                Utils.nonNullIntValue(frame.getStartPos().getX()),
                Utils.nonNullIntValue(frame.getStartPos().getY()),
                Utils.nonNullIntValue(frame.getStopPos().getY()),
                color
        );
        //Right line
        drawVerticalLine(
                matrix,
                Utils.nonNullIntValue(frame.getStopPos().getX()),
                Utils.nonNullIntValue(frame.getStartPos().getY()),
                Utils.nonNullIntValue(frame.getStopPos().getY()),
                color
        );
    }

    public void drawVerticalLine(MatrixStack matrix, Pos startPos, Pos stopPos, int color) {
        drawVerticalLine(
                matrix,
                Utils.nonNullIntValue(startPos.getX()),
                Utils.nonNullIntValue(startPos.getY()),
                Utils.nonNullIntValue(stopPos.getY()),
                color
        );
    }
}
