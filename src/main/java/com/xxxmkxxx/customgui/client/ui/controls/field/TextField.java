package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.Pos;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextField extends AbstractField {
    @Getter
    private Text text;
    private int textColor;

    private TextField() {}

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

    public static class Builder implements SimpleBuilder<TextField> {
        private TextField textField = new TextField();

        public Builder text(String str) {
            textField.text = Text.of(str);

            return this;
        }

        public Builder text(Text text) {
            textField.text = text;

            return this;
        }

        public Builder textColor(int color) {
            textField.textColor = color;

            return this;
        }

        public Builder matrix(MatrixStack matrixStack) {
            textField.matrixStack = matrixStack;

            return this;
        }

        public Builder pos(Pos pos) {
            textField.pos = pos;

            return this;
        }

        public Builder pos(int x, int y) {
            textField.pos = new Pos(x, y);

            return this;
        }

        @Override
        public TextField build() {
            return textField;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<TextField> {
        @Override
        public NodeRenderer<TextField> create(RendererType type) {
            switch(type) {
                case HUD: {
                    return node -> {
                        CustomGUIClient.NODE_DRAWABLE_HELPER.drawText(
                                node.matrixStack,
                                node.text,
                                node.pos,
                                node.textColor
                        );
                    };
                }

                case SCREEN: {
                    return node -> {
                        this.create(RendererType.HUD).render(node);
                    };
                }

                default: return node -> {};
            }
        }
    }
}
