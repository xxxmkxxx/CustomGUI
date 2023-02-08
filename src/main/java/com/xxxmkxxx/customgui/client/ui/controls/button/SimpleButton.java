package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
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
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public class SimpleButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    @SuppressWarnings("CodeBlock2Expr")
    protected SimpleButton(Pos start, String name, Style style) {
        super(start, name);
        this.style = style;
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
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);
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

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(this, this);
    }

    public void setHoverAction(Runnable hoverAction) {
        this.hoverAction = hoverAction;
        EventBus.HOVER_EVENT.addHandler(this, this);
    }

    public void setResetHoverAction(Runnable resetHoverAction) {
        this.resetHoverAction = resetHoverAction;
        EventBus.RESET_HOVER_EVENT.addHandler(this, this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String buttonName = "button";
        private Pos startPos = Pos.DEFAULT_POS;
        private Style style = Style.DEFAULT_STYLE;

        public Builder text(String text) {
            this.buttonName = text;
            return this;
        }

        public Builder startPos(Pos pos) {
            this.startPos = pos;
            return this;
        }

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        public SimpleButton build() {
            return new SimpleButton(startPos, buttonName, style);
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
