package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.button.StandardButtonAnimations;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.ActionBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SimpleButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    protected SimpleButton(Pos startPos, Pos stopPos, SimpleText text) {
        super(startPos, stopPos, text);
        this.leftClickAction = () -> {
            EventManager.sendAction(
                    rendererType,
                    ActionBuilder.of()
                            .addSimpleAnimation(
                                    "standard_left_click_button_animation",
                                    StandardButtonAnimations.LEFT_CLICK.getAnimation(),
                                    this,
                                    1
                            )
            );
        };
        this.hoverAction = () -> {
            EventManager.sendAction(
                    rendererType,
                    ActionBuilder.of()
                            .addStickyAnimation(
                                    "standard_hover_animation",
                                    StandardButtonAnimations.HOVER.getAnimation(),
                                    this,
                                    1
                            )
            );
        };
        this.resetHoverAction = () -> {
            EventManager.sendAction(
                    rendererType,
                    ActionBuilder.of()
                            .deleteStickyAnimation("standard_hover_animation")
            );
        };
        updateIndents();
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
    }

    @Override
    public void onHover() {
        hoverAction.run();
    }

    @Override
    public void onResetHover() {
        resetHoverAction.run();
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new RendererFactory().create(type);
        text.initRenderer(type);
    }

    @Override
    public void update() {
        updateIndents();
    }

    @SuppressWarnings("DuplicatedCode")
    private void updateIndents() {
        float leftButtonMargin = style.getMargins().getLeft();
        float topButtonMargin = style.getMargins().getTop();
        float leftButtonPadding = style.getPaddings().getLeft();
        float topButtonPadding = style.getPaddings().getTop();
        float leftTextMargin = text.getStyle().getMargins().getLeft();
        float topTextMargin = text.getStyle().getMargins().getTop();

        frame.moveStartPos(leftButtonMargin, topButtonMargin);

        float tempXDistance = leftButtonMargin + leftButtonPadding + leftTextMargin;
        float tempYDistance = topButtonMargin + topButtonPadding + topTextMargin;

        text.getFrame().moveStartPos(tempXDistance, tempYDistance);
        text.getFrame().moveStopPos(tempXDistance, tempYDistance);

        frame.moveStopPos(leftButtonMargin, topButtonMargin);
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
    }

    @Override
    public void hide() {
        super.hide();
        text.hide();
    }

    @Override
    public void display() {
        super.display();
        text.display();
    }

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(getId(), this);
    }

    public void setHoverAction(Runnable hoverAction) {
        this.hoverAction = hoverAction;
        EventBus.HOVER_EVENT.addHandler(getId(), this);
    }

    public void setResetHoverAction(Runnable resetHoverAction) {
        this.resetHoverAction = resetHoverAction;
        EventBus.RESET_HOVER_EVENT.addHandler(getId(), this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractNodeBuilder<SimpleButton> {
        private SimpleText textNode;
        private String text;

        public Builder() {
            super();
            this.text = "button";
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

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder text(SimpleText text) {
            this.textNode = text;
            return this;
        }

        public SimpleButton build() {
            SimpleText text = textNode == null ? SimpleText.builder().startPos(startPos).text(this.text).build() : this.textNode;
            Pos stopPos;

            try {
                stopPos = this.stopPos == null ? (Pos) text.getFrame().getStopPos().clone() : this.stopPos;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

            SimpleButton button = new SimpleButton(startPos, stopPos, text);
            button.setStyle(style);

            return button;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleButton> {
        private final ParametrizedSelfDestructionMethod<SimpleButton> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();

        private Consumer<SimpleButton> renderBackgroundMethod = (button) -> {};

        public RendererFactory() {
            initBackgroundMethod.setAction(button -> {
                renderBackgroundMethod = Background.chooseBackground(button.getStyle().getBackground().getType());
            });
        }

        @Override
        public NodeRenderer<SimpleButton> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleButton button) {
            initBackgroundMethod.execute(button);

            renderBackgroundMethod.accept(button);

            CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getStyle().getMatrixStack(),
                    button.getFrame(),
                    button.getStyle().getHexBackgroundColor()
            );

            button.getText().getState().execute(button.getText(), button.getText().getRenderer());
        }
    }
}
