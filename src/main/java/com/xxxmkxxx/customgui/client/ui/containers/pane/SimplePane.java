package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.LinkedList;
import java.util.List;

@Getter
@SuppressWarnings("unused")
public class SimplePane extends AbstractPane {
    protected final List<AbstractNode> nodes;
    @Setter
    private int color;

    protected SimplePane(AbstractFrame frame) {
        Validator.checkNullObject(frame);
        this.frame = frame;
        nodes = new LinkedList<>();
    }

    protected SimplePane(AbstractFrame frame, List<AbstractNode> nodes) {
        Validator.checkNullObject(frame);
        Validator.checkNullObject(nodes);
        this.nodes = nodes;
    }

    @Override
    public void initRenderer(RendererType type) {
        this.renderer = new RendererFactory().create(type);
    }

    public void addNode(AbstractNode node) {
        Validator.checkNullObject(node);
        nodes.add(node);
    }

    public static Builder builder(AbstractFrame frame, List<AbstractNode> nodes) {
        return new Builder(frame, nodes);
    }
    public static Builder builder(AbstractFrame frame) {
        return new Builder(frame);
    }

    public static final class Builder implements SimpleBuilder<SimplePane> {
        private final SimplePane simplePane;

        public Builder(AbstractFrame frame) {
            simplePane = new SimplePane(frame);
        }

        public Builder(AbstractFrame frame, List<AbstractNode> nodes) {
            simplePane = new SimplePane(frame, nodes);
        }

        public Builder color(int hexColorCode) {
            Validator.checkNullObject(hexColorCode);
            simplePane.setColor(hexColorCode);

            return this;
        }

        public Builder matrixStack(MatrixStack matrixStack) {
            Validator.checkNullObject(matrixStack);
            simplePane.matrixStack = matrixStack;

            return this;
        }

        @Override
        public SimplePane build() {
            return simplePane;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimplePane> {
        @Override
        public NodeRenderer<SimplePane> create(RendererType type) {
            return switch (type) {
                case HUD -> this::render;
                case SCREEN -> this::renderWithBackground;
            };
        }

        @SuppressWarnings("unchecked")
        private void render(SimplePane simplePane) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    simplePane.getMatrixStack(),
                    simplePane.getFrame(),
                    simplePane.getColor()
            );

            simplePane.getNodes().forEach(node -> node.getRenderer().render(node));
        }

        private void renderWithBackground(SimplePane simplePane) {
            final int width = MinecraftClient.getInstance().getWindow().getWidth();
            final int height = MinecraftClient.getInstance().getWindow().getHeight();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            CustomGUIClient.NODE_DRAWABLE_HELPER.gradientFillFrame(
                    simplePane.getMatrixStack(),
                    0, 0,
                    width, height
            );

            render(simplePane);
        }
    }
}