package com.xxxmkxxx.customgui.client.ui.controls.label;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import net.minecraft.text.Text;

import java.util.function.Function;

public class SimpleLabel extends AbstractLabel implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter
    private SimpleText text;
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable resetHoverAction = () -> {};

    protected SimpleLabel(AbstractNode pointer, Text text, Position position) {
        this.text = SimpleText.builder().text(text).build();
        this.pointer = pointer;
        this.position = position;
        arrangeRelatively(pointer, position);
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

    @Override
    public AbstractNode arrangeRelatively(AbstractNode node, Position position) {
        super.arrangeRelatively(node, position);
        text.arrangeRelatively(node, position);
        return this;
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        text.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);
        text.initSection(initMethod);
    }

    @Override
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        text.scaling(widthScaleValue, heightScaleValue);
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

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    public static class RendererFactory implements NodeRendererFactory<SimpleLabel> {
        @Override
        public NodeRenderer<SimpleLabel> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleLabel simpleLabel) {
            simpleLabel.getText().getState().execute(simpleLabel.getText(), simpleLabel.getText().getRenderer());
        }
    }

    public static class Builder {
        protected Style style = Style.defaultStyle();
        protected Text text = Text.of("");
        protected AbstractNode.Position position = Position.LEFT;

        public Builder text(String text) {
            this.text = Text.of(text);
            return this;
        }

        public Builder text(Text text) {
            this.text = text;
            return this;
        }

        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        public SimpleLabel build(AbstractNode pointer) {
            return new SimpleLabel(pointer, text, position);
        }
    }
}
