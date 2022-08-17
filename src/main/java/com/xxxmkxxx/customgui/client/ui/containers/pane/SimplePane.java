package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.NodeState;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

@Getter
public class SimplePane extends AbstractPane {
    private int color;

    private SimplePane() {}

    @Override
    public void hide() {
        this.state = States.HIDED;
    }

    @Override
    public void display() {
        this.state = States.DISPLAYED;
    }

    @Override
    public void initRenderer(RendererType type) {
        this.renderer = new RendererFactory().create(type);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements SimpleBuilder<SimplePane> {
        private final SimplePane simplePane = new SimplePane();

        public Builder color(int hexColorCode) {
            simplePane.color = hexColorCode;

            return this;
        }

        public Builder state(NodeState<AbstractNode> state) {
            simplePane.state = state;

            return this;
        }

        public Builder frame(Frame frame) {
            simplePane.frame = frame;

            return this;
        }

        public Builder matrixStack(MatrixStack matrixStack) {
            simplePane.matrixStack = matrixStack;

            return this;
        }

        public Builder nodes(List<AbstractNode> nodes) {
            simplePane.nodes = nodes;

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
            switch (type) {
                case HUD: return node -> {
                    CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                            node.getMatrixStack(),
                            node.getFrame(),
                            node.getColor()
                    );
                };

                case SCREEN: return node -> {
                    final int width = MinecraftClient.getInstance().getWindow().getWidth();
                    final int height = MinecraftClient.getInstance().getWindow().getHeight();

                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    CustomGUIClient.NODE_DRAWABLE_HELPER.gradientFillFrame(
                            node.getMatrixStack(),
                            0, 0,
                            width, height
                    );

                    this.create(RendererType.HUD).render(node);
                };

                default: return node -> {};
            }
        }
    }
}