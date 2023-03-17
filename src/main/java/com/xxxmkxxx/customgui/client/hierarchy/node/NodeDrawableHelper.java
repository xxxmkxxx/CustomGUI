package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
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

    public void gradientFillFrame(MatrixStack matrix, int startX, int startY, int stopX, int stopY, int colorStart, int colorEnd) {
        fillGradient(matrix, startX, startY, stopX, stopY, colorStart, colorEnd);
    }

    public void gradientFillFrame(MatrixStack matrix, int startX, int startY, int stopX, int stopY) {
        fillGradient(matrix, startX, startY, stopX, stopY, startGradientColor, stopGradientColor);
    }

    public void gradientFillFrame(MatrixStack matrix, AbstractFrame frame, int colorStart, int colorEnd) {
        gradientFillFrame(
                matrix,
                frame.getStartPos().getX(), frame.getStartPos().getY(),
                frame.getStopPos().getX(), frame.getStopPos().getY(),
                colorStart, colorEnd
        );
    }

    public void fillFrame(MatrixStack matrix, int startX, int startY, int stopX, int stopY, int color) {
        fill(matrix, startX, startY, stopX, stopY, color);
    }

    public void fillFrame(MatrixStack matrix, AbstractFrame frame, int color) {
        fillFrame(
                matrix,
                frame.getStartPos().getX(), frame.getStartPos().getY(),
                frame.getStopPos().getX(), frame.getStopPos().getY(),
                color
        );
    }

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

    public void drawText(MatrixStack matrix, Text text, int x, int y, int color) {
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
        int width = frame.getStopPos().getX() - frame.getStartPos().getX();
        int height = frame.getStopPos().getY() - frame.getStartPos().getY();
        drawTexture(stack, frame.getStartPos(), width, height);
    }

    public void drawTexture(ItemStack stack, Pos pos, int width, int height) {
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
        drawHorizontalLine(matrix, frame.getStartPos().getX(), frame.getStopPos().getX(), frame.getStartPos().getY(), color);
        //Down line
        drawHorizontalLine(matrix, frame.getStartPos().getX(), frame.getStopPos().getX(), frame.getStopPos().getY(), color);
        //Left line
        drawVerticalLine(matrix, frame.getStartPos().getX(), frame.getStartPos().getY(), frame.getStopPos().getY(), color);
        //Right line
        drawVerticalLine(matrix, frame.getStopPos().getX(), frame.getStartPos().getY(), frame.getStopPos().getY(), color);
    }

    public void drawVerticalLine(MatrixStack matrix, Pos startPos, Pos stopPos, int color) {
        drawVerticalLine(matrix, startPos.getX(), startPos.getY(), stopPos.getY(), color);
    }
}
