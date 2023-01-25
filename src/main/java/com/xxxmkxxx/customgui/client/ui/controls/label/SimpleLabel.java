package com.xxxmkxxx.customgui.client.ui.controls.label;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.controls.text.AbstractText;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import net.minecraft.text.Text;

public class SimpleLabel extends AbstractLabel implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable resetHoverAction = () -> {};

    private SimpleLabel(AbstractNode pointer, AbstractText text, Position position, int indent) {
        text.arrangeRelatively(pointer, position, indent);
        this.pointer = pointer;
        this.text = text;
        this.position = position;
        this.indent = indent;
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
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        text.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
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

    public static Builder builder(AbstractNode pointer) {
        return new Builder(pointer);
    }

    @SuppressWarnings("unchecked")
    public static class RendererFactory implements NodeRendererFactory<SimpleLabel> {
        @Override
        public NodeRenderer<SimpleLabel> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleLabel simpleLabel) {
            simpleLabel.getText().getRenderer().render(simpleLabel.getText());
        }
    }

    public static class Builder {
        protected AbstractText text = SimpleText.builder().build();
        protected AbstractNode.Position position = Position.LEFT;
        protected AbstractNode pointer;
        protected int indent = 1;

        public Builder(AbstractNode pointer) {
            this.pointer = pointer;
        }

        public Builder text(String text) {
            this.text = SimpleText.builder().text(text).build();
            return this;
        }

        public Builder text(Text text) {
            this.text = SimpleText.builder().text(text).build();
            return this;
        }

        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        public Builder indent(int indent) {
            this.indent = indent;
            return this;
        }

        public SimpleLabel build() {
            return new SimpleLabel(pointer, text, position, indent);
        }
    }
}
