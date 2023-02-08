package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.inputfield.StandardInputFieldAnimations;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.ActionBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.change.ChangeEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character.KeyboardCharInputEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key.KeyboardKeyInputEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.cursor.InputCursor;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

@Getter @Setter
public class InputField extends AbstractField implements LeftClickEventHandler, ChangeEventHandler, KeyboardCharInputEventHandler, KeyboardKeyInputEventHandler {
    private SimpleText text;
    private InputCursor inputCursor;
    private Runnable leftClickAction = () -> {};
    private Runnable changeAction = () -> {};

    protected InputField(Pos pos, int width, int height) {
        this.frame = new SimpleFrame(pos, width, height);
        this.text = SimpleText.builder()
                .style(new Style())
                .pos(pos)
                .text("")
                .build();
        this.inputCursor = InputCursor.builder()
                .pos(pos)
                .width(1)
                .height(text.getTextHeight())
                .style(new Style())
                .build();
        inputCursor.hide();
    }

    @Override
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        text.scaling(widthScaleValue, heightScaleValue);
        inputCursor.scaling(widthScaleValue, heightScaleValue);
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        super.init(initMethod);
        text.init(initMethod);
        inputCursor.init(initMethod);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
        text.initRenderer(type);
        inputCursor.initRenderer(type);
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
        EventManager.sendAction(
                rendererType,
                ActionBuilder.of().unblockKeyboard().activeNode(this).addCyclicAnimation(
                        "standard_input_field_left_click_animation",
                        StandardInputFieldAnimations.LEFT_CLICK.getAnimation(),
                        this
                )
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
        text.setText(Text.of(textBuilder.toString()));
        inputCursor.move(Utils.getTextWidth(Text.of(String.valueOf(symbol))), 0);
        onChange();
    }

    @Override
    public void onKeyInput(int key) {
        changeKeyAction(key).run();
    }

    @Override
    public AbstractNode arrangeRelatively(AbstractNode node, Position position, int indent) {
        super.arrangeRelatively(node, position, indent);
        //inputCursor.updatePos(frame.getStartPos());
        return this;
    }

    public static Builder builder() {
        return new Builder();
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
            case GLFW.GLFW_KEY_ENTER:
            case GLFW.GLFW_KEY_ESCAPE: {
                return () -> {
                    EventManager.sendAction(
                            rendererType,
                            ActionBuilder.of()
                                    .blockKeyboard()
                                    .deleteCyclicAnimation("standard_input_field_left_click_animation")
                                    .activeNode(EMPTY_NODE)
                    );

                    EventBus.KEYBOARD_CHAR_INPUT_EVENT.removeHandler(this);
                    EventBus.KEYBOARD_KEY_INPUT_EVENT.removeHandler(this);
                };
            }
            default: return () -> {};
        }
    }

    public static class Builder {
        private Style style = Style.DEFAULT_STYLE;

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        public InputField build(Pos startPos, int width, int height) {
            InputField inputField = new InputField(startPos, width, height);

            return inputField;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<InputField> {
        private final ParametrizedSelfDestructionMethod<InputField> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<InputField> backgroundRendererMethod = inputField -> {};

        public RendererFactory() {
            initBackgroundMethod.setAction(inputField -> {
                backgroundRendererMethod = Background.chooseBackground(inputField.getStyle().getBackground().getType());
            });
        }

        @Override
        public NodeRenderer<InputField> create(RendererType type) {
            return this::render;
        }

        private void render(InputField inputField) {
            initBackgroundMethod.execute(inputField);

            backgroundRendererMethod.accept(inputField);

            inputField.getText().getState().execute(inputField.getText(), inputField.getText().getRenderer());
            inputField.getInputCursor().getState().execute(inputField.getInputCursor(), inputField.getInputCursor().getRenderer());
        }
    }
}
