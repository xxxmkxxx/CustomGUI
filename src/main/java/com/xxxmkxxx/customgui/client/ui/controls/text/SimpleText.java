package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Color;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;

@Getter
public class SimpleText extends AbstractText implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable  resetHoverAction = () -> {};

    protected SimpleText(Pos startPos, Text text, Style style) {
        super(startPos, text);
        this.style = style;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleText> {
        private Consumer<SimpleText> textRenderMethod;

        public RendererFactory() {
            this.textRenderMethod = simpleText -> {
                CustomGUI.NODE_DRAWABLE_HELPER.drawText(
                        simpleText.getStyle().getMatrixStack(),
                        simpleText.getText(),
                        simpleText.getStartPos(),
                        simpleText.getStyle().getHexColor()
                );
            };
        }

        @Override
        public NodeRenderer<SimpleText> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleText simpleText) {
            textRenderMethod.accept(simpleText);
        }
    }

    public static class Builder {
        private Pos startPos = Pos.DEFAULT_POS;
        private Text text = Text.of("text");
        private Style style = Style.DEFAULT_STYLE;

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        public Builder text(String text) {
            this.text = Text.of(text);
            return this;
        }

        public Builder text(Text text) {
            this.text = text;
            return this;
        }

        public Builder pos(int x, int y) {
            this.startPos = new Pos(x, y);
            return this;
        }

        public Builder pos(Pos pos) {
            this.startPos = pos;
            return this;
        }

        public SimpleText build() {
            return new SimpleText(startPos, text, style);
        }
    }
}
