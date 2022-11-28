package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
@Getter
@SuppressWarnings("unused")
public class TextField extends AbstractField {
    private TextField() {
        this.frame = new DynamicFrame(0, 0, 18, 18, false);
    }

    @Override
    public void initRenderer(RendererType type) {
        this.renderer = new RendererFactory().create(type);
    }

    public void setText(String text) {
        Validator.checkNullObject(text);
        ((DynamicFrame)this.frame).setStopPos(text.length() * 3, frame.getStopPos().y());
        this.textBuilder = new StringBuilder(text);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements SimpleBuilder<TextField> {
        private final TextField textField = new TextField();

        public Builder text(String text) {
            textField.setText(text);

            return this;
        }

        public Builder textColor(int color) {
            textField.setTextColor(color);

            return this;
        }

        public Builder matrix(MatrixStack matrixStack) {
            textField.setMatrixStack(matrixStack);

            return this;
        }

        public Builder pos(int x, int y) {
            ((DynamicFrame)textField.getFrame()).setStartPos(new Pos(x, y));

            return this;
        }
        public Builder pos(Pos pos) {
            Validator.checkNullObject(pos);
            ((DynamicFrame)textField.getFrame()).setStartPos(pos);

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
            return this::render;
        }

        private void render(TextField field) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.drawText(
                    field.getMatrixStack(),
                    Text.of(field.getTextBuilder().toString()),
                    field.getFrame().getStartPos(),
                    field.getTextColor()
            );
        }
    }
}
