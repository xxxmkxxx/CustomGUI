package com.xxxmkxxx.customgui.client.ui.controls.label;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.layout.LayoutManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.layout.Position;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;

import java.util.function.Function;

public class SimpleLabel extends AbstractLabel implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter
    private SimpleText text;
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable resetHoverAction = () -> {};

    protected SimpleLabel(AbstractNode pointer, SimpleText text, Position position) {
        this.text = text;
        this.pointer = pointer;
        this.position = position;
        this.frame = SimpleFrame.builder().positions(text.getFrame()).build();
        updateIndents();
        System.out.println("label text" + text.getFrame());
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
    public void update() {
        updateIndents();
    }

    private void updateIndents() {
        float leftLabelMargin = style.getMargins().getLeft();
        float topLabelMargin = style.getMargins().getTop();

        frame.moveStartPos(leftLabelMargin, topLabelMargin);
        frame.moveStopPos(leftLabelMargin, topLabelMargin);
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);
        text.initSection(initMethod);
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        text.scaling(window);
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
        protected Style style;
        protected String text;
        protected SimpleText textNode;
        protected Position position;

        public Builder() {
            this.style = Style.defaultStyle();
            this.text = "label";
            this.position = Position.LEFT;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder text(SimpleText text) {
            this.textNode = text;
            return this;
        }

        public Builder style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder relativePosition(Position position) {
            this.position = position;
            return this;
        }

        public SimpleLabel build(AbstractNode pointer) {
            SimpleText text;

            if (this.textNode == null) {
                Pos startPos;

                try {
                    startPos = (Pos) pointer.getFrame().getStartPos().clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }

                text = SimpleText.builder().startPos(startPos).text(this.text).build();
                LayoutManager.positionNodeRelativeTargetNode(pointer, text, position);
            } else {
                text = this.textNode;
            }

            SimpleLabel simpleLabel = new SimpleLabel(pointer, text, position);
            simpleLabel.setStyle(style);

            LayoutManager.positionNodeRelativeTargetNode(pointer, simpleLabel, position);

            return simpleLabel;
        }
    }
}
