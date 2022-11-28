package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.ActionBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character.KeyboardCharInputEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.change.ChangeEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key.KeyboardKeyInputEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Getter @Setter
public class InputField extends AbstractField implements LeftClickEventHandler, ChangeEventHandler, KeyboardCharInputEventHandler, KeyboardKeyInputEventHandler {
    private String promptText;
    private int promptTextColor = 0xAF647f8f;
    private Runnable leftClickAction = () -> {};
    private Runnable changeAction = () -> {};

    public InputField(Pos startPos, int width, int height) {
        this.frame = new DynamicFrame(startPos, width, height, false);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
        EventManager.sendAction(
                rendererType,
                ActionBuilder.of().unblockKeyboard().activeNode(this)
        );

        EventBus.KEYBOARD_CHAR_INPUT_EVENT.addHandler(this, this);
        EventBus.KEYBOARD_KEY_INPUT_EVENT.addHandler(this, this);
    }

    @Override
    public void onChange() {
        changeAction.run();
    }

    @Override
    public void onCharInput(char symbol) {
        textBuilder.append(symbol);
        text = textBuilder.toString();
        System.out.println(symbol);
        onChange();
    }

    @Override
    public void onKeyInput(int key) {
        changeKeyAction(key).run();
    }

    public static Builder builder(Pos startPos, int width, int height) {
        return new Builder(startPos, width, height);
    }

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(this, this);
    }

    public void setChangeAction(Runnable changeAction) {
        this.changeAction = changeAction;
        EventBus.CHANGE_EVENT.addHandler(this, this);
    }

    private Runnable changeKeyAction(int keyCode) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_ENTER: {
                return () -> {
                    EventManager.sendAction(
                            rendererType,
                            ActionBuilder.of().blockKeyboard().activeNode(EMPTY_NODE)
                    );

                    EventBus.KEYBOARD_CHAR_INPUT_EVENT.removeHandler(this);
                    EventBus.KEYBOARD_KEY_INPUT_EVENT.removeHandler(this);

                    System.out.println(text);
                };
            }
            default: return () -> {};
        }
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
                    Text.of(inputField.getText()),
                    inputField.getFrame().getStartPos(),
                    0xFFf50707
            );
        }
    }
}
