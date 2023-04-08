package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.NodeBuilder;
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
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.cursor.InputCursor;
import com.xxxmkxxx.customgui.client.ui.controls.field.event.send.FieldSendEventHandler;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

@Getter @Setter
public class NoneExpandableInputField extends AbstractField implements LeftClickEventHandler, ChangeEventHandler, KeyboardCharInputEventHandler, KeyboardKeyInputEventHandler, FieldSendEventHandler {
    private SimpleText text;
    private SimpleText promptText;
    private InputCursor inputCursor;
    private Runnable leftClickAction = () -> {};
    private Runnable changeAction = () -> {};
    private FieldSendEventHandler sendAction = (text) -> {};

    protected NoneExpandableInputField(Pos startPos, Pos stopPos, SimpleText promptText, Style style) {
        this.style = style;
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
        this.text = SimpleText.builder().style(style).startPos(startPos).text("").build();
        this.promptText = promptText;
        this.inputCursor = InputCursor.builder()
                .startPos(startPos)
                .widthPercent(0.3f)
                .heightPercent(text.getFrame().getStopPos().getYIndentPercent() - text.getFrame().getStartPos().getYIndentPercent())
                .style(style)
                .build();
        inputCursor.hide();
        updateIndents();
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        text.scaling(window);
        promptText.scaling(window);
        inputCursor.scaling(window);
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        super.init(initMethod);
        text.init(initMethod);
        promptText.init(initMethod);
        inputCursor.init(initMethod);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
        text.initRenderer(type);
        promptText.initRenderer(type);
        inputCursor.initRenderer(type);
    }

    @Override
    public void update() {
        updateIndents();
    }

    private void updateIndents() {
        float leftInputFieldMargin = style.getMargins().getLeft();
        float topInputFieldMargin = style.getMargins().getTop();

        float tempXOffset = leftInputFieldMargin;
        float tempYOffset = topInputFieldMargin;

        frame.moveStartPos(tempXOffset, tempYOffset);
        frame.moveStopPos(tempXOffset, tempYOffset);

        tempXOffset += style.getPaddings().getLeft() + text.getStyle().getMargins().getLeft();
        tempYOffset += style.getPaddings().getTop() + text.getStyle().getMargins().getTop();

        text.getFrame().moveStartPos(tempXOffset, tempYOffset);
        text.getFrame().moveStopPos(tempXOffset, tempYOffset);
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
        promptText.hide();

        EventManager.sendAction(
                rendererType,
                ActionBuilder.of().unblockKeyboard().activeNode(this).addCyclicAnimation(
                        "standard_input_field_left_click_animation",
                        StandardInputFieldAnimations.LEFT_CLICK.getAnimation(),
                        this
                )
        );

        EventBus.KEYBOARD_CHAR_INPUT_EVENT.addHandler(getId(), this);
        EventBus.KEYBOARD_KEY_INPUT_EVENT.addHandler(getId(), this);
    }

    @Override
    public void onChange() {
        changeAction.run();
    }

    @Override
    public void onCharInput(char symbol) {
        textBuilder.append(symbol);
        text.setText(Text.of(textBuilder.toString()));
        inputCursor.move(Utils.getSymbolWidth(symbol, style.getFont()) + style.getFont().getSymbolPaddingPx(), 0);
        onChange();
    }

    @Override
    public void onKeyInput(int key) {
        changeKeyAction(key).run();
    }

    @Override
    public void onSend(String text) {
        sendAction.onSend(text);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(getId(), this);
    }

    public void setChangeAction(Runnable changeAction) {
        this.changeAction = changeAction;
        EventBus.CHANGE_EVENT.addHandler(getId(), this);
    }

    public void setSendAction(FieldSendEventHandler sendAction) {
        this.sendAction = sendAction;
        EventBus.FIELD_SEND_EVENT.addHandler(getId(), this);
    }

    private Runnable changeKeyAction(int keyCode) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_ENTER: EventBus.FIELD_SEND_EVENT.callHandler(getId(), textBuilder.toString());
            case GLFW.GLFW_KEY_ESCAPE: {
                return () -> {
                    EventManager.sendAction(
                            rendererType,
                            ActionBuilder.of()
                                    .blockKeyboard()
                                    .deleteCyclicAnimation("standard_input_field_left_click_animation")
                                    .activeNode(EMPTY_NODE)
                    );

                    if (textBuilder.isEmpty()) promptText.display();

                    EventBus.KEYBOARD_CHAR_INPUT_EVENT.removeHandler(getId());
                    EventBus.KEYBOARD_KEY_INPUT_EVENT.removeHandler(getId());
                };
            }
            default: return () -> {};
        }
    }

    public static class Builder extends AbstractNodeBuilder<NoneExpandableInputField> {
        private float widthPercent;
        private float heightPercent;
        private String promptText;
        private SimpleText promptTextNode;

        public Builder() {
            super();
            this.widthPercent = style.getFont().getXSizePercent() * 7;
            this.heightPercent = style.getFont().getYSizePercent();
            this.promptText = "prompt";
        }

        @Override
        public Builder startPos(Pos pos) {
            return (Builder) super.startPos(pos);
        }

        @Override
        public Builder stopPos(Pos pos) {
            return (Builder) super.stopPos(pos);
        }

        @Override
        public Builder positions(Pos startPos, Pos stopPos) {
            return (Builder) super.positions(startPos, stopPos);
        }

        @Override
        public Builder positions(AbstractFrame frame) {
            return (Builder) super.positions(frame);
        }

        @Override
        public Builder style(Style style) {
            return (Builder) super.style(style);
        }

        public Builder promptText(String promptText) {
            this.promptText = promptText;
            return this;
        }

        public Builder promptTextNode(SimpleText promptTextNode) {
            this.promptTextNode = promptTextNode;
            return this;
        }

        public Builder widthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        public Builder heightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
            return this;
        }

        public NoneExpandableInputField build() {
            Pos stopPos = this.stopPos == null
                    ? Pos.builder().relativeCoords(
                            startPos.getXIndentPercent() + widthPercent,
                            startPos.getYIndentPercent() + heightPercent
                    )
                    .proportionBy(startPos.getProportionBy())
                    .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                    : this.stopPos;

            Style promptStyle;

            try {
                promptStyle = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

            promptStyle.getFont().getOpacity().setPercent(50);

            SimpleText promptTextNode = this.promptTextNode == null
                    ? SimpleText.builder().text(promptText).style(promptStyle).startPos(startPos).build()
                    : this.promptTextNode;



            return new NoneExpandableInputField(startPos, stopPos, promptTextNode, style);
        }
    }

    public static class RendererFactory implements NodeRendererFactory<NoneExpandableInputField> {
        private final ParametrizedSelfDestructionMethod<NoneExpandableInputField> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<NoneExpandableInputField> backgroundRendererMethod = noneExpandableInputField -> {};

        public RendererFactory() {
            initBackgroundMethod.setAction(noneExpandableInputField -> {
                backgroundRendererMethod = Background.chooseBackground(noneExpandableInputField.getStyle().getBackground().getType());
            });
        }

        @Override
        public NodeRenderer<NoneExpandableInputField> create(RendererType type) {
            return this::render;
        }

        private void render(NoneExpandableInputField noneExpandableInputField) {
            initBackgroundMethod.execute(noneExpandableInputField);

            backgroundRendererMethod.accept(noneExpandableInputField);

            noneExpandableInputField.getText().getState().execute(noneExpandableInputField.getText(), noneExpandableInputField.getText().getRenderer());
            noneExpandableInputField.getPromptText().getState().execute(noneExpandableInputField.getPromptText(), noneExpandableInputField.getPromptText().getRenderer());
            noneExpandableInputField.getInputCursor().getState().execute(noneExpandableInputField.getInputCursor(), noneExpandableInputField.getInputCursor().getRenderer());
        }
    }
}
