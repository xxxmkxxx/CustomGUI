package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.Text;

@Getter @Setter
public class InputField extends AbstractField implements LeftClickEventHandler {
    private String promptText;
    private int promptTextColor = 0xAF647f8f;
    private Runnable leftClickAction = () -> {};

    public InputField(Pos startPos, int width, int height) {
        this.frame = new StaticFrame(startPos, width, height, false);
    }

    @Override
    public void initRenderer(RendererType type) {
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
    }

    public static Builder builder(Pos startPos, int width, int height) {
        return new Builder(startPos, width, height);
    }

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(this, this);
    }

    public static class Builder implements SimpleBuilder<InputField> {
        private final InputField inputField;

        public Builder(Pos startPos, int width, int height) {
            inputField = new InputField(startPos, width, height);
        }

        public Builder promptText(String text) {
            inputField.setPromptText(text);
            return this;
        }

        public Builder promptTextColor(int color) {
            inputField.setPromptTextColor(color);
            return this;
        }

        @Override
        public InputField build() {
            return inputField;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<InputField> {
        @Override
        public NodeRenderer<InputField> create(RendererType type) {
            return this::render;
        }

        private void render(InputField inputField) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    inputField.getMatrixStack(),
                    inputField.getFrame(),
                    0xAF4b4e4f
            );

            CustomGUIClient.NODE_DRAWABLE_HELPER.drawText(
                    inputField.getMatrixStack(),
                    Text.of(inputField.getPromptText()),
                    inputField.getFrame().getStartPos(),
                    0xFFf50707
            );
        }
    }
}
