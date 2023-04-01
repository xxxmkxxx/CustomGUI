package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import net.minecraft.text.Text;

import java.util.function.Consumer;

@Getter
public class SimpleText extends AbstractText implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable  resetHoverAction = () -> {};

    protected SimpleText(Pos startPos, Pos stopPos, Text text, Style style) {
        super(startPos, stopPos, text, style);
        updateIndents();
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

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void update() {
        updateIndents();
    }

    private void updateIndents() {
        float leftTextMargin = style.getMargins().getLeft();
        float topTextMargin = style.getMargins().getTop();

        frame.moveStartPos(leftTextMargin, topTextMargin);
        frame.moveStopPos(leftTextMargin, topTextMargin);
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
        private final Consumer<SimpleText> textRenderMethod;

        public RendererFactory() {
            this.textRenderMethod = simpleText -> {
                CustomGUI.NODE_DRAWABLE_HELPER.drawText(
                        simpleText.getMatrixStack(),
                        simpleText.getText(),
                        simpleText.getFrame().getStartPos().getX(),
                        simpleText.getFrame().getStartPos().getY(),
                        simpleText.getStyle().getFont()
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
        private Pos startPos;
        private Text text;
        private Style style;

        public Builder() {
            this.startPos = Pos.defaultPos();
            this.text = Text.of("text");
            this.style = Style.defaultStyle();
        }

        public Builder style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
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

        public Builder startPos(Pos pos) {
            try {
                this.startPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public SimpleText build() {
            Pos stopPos = Pos.builder()
                    .coords(
                            startPos.getX() + Utils.getTextWidth(text.getString(), style.getFont()),
                            startPos.getY() + style.getFont().getYSizePx()
                    )
                    .build(startPos.getXPercentValue(), startPos.getYPercentValue());

            return new SimpleText(startPos, stopPos, text, style);
        }
    }
}
