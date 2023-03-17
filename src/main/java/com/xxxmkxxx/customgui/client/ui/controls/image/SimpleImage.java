package com.xxxmkxxx.customgui.client.ui.controls.image;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class SimpleImage extends AbstractImage implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable  resetHoverAction = () -> {};

    protected SimpleImage(Pos startPos, Pos stopPos, Identifier imageIdentifier) {
        super(startPos, stopPos, imageIdentifier);
        updateIndents();
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
    public void update() {
        updateIndents();
    }

    private void updateIndents() {
        float leftImageMargin = style.getMargins().getLeft();
        float topImageMargin = style.getMargins().getTop();

        frame.moveStartPos(leftImageMargin, topImageMargin);
        frame.moveStopPos(leftImageMargin, topImageMargin);
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

    public static class RendererFactory implements NodeRendererFactory<SimpleImage> {
        private Consumer<SimpleImage> imageRenderMethod;

        public RendererFactory() {
            imageRenderMethod = simpleImage -> {
                CustomGUI.NODE_DRAWABLE_HELPER.drawTexture(
                        simpleImage.getStyle().getMatrixStack(),
                        simpleImage.getFrame(),
                        simpleImage.getImageIdentifier()
                );
            };
        }

        @Override
        public NodeRenderer<SimpleImage> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleImage simpleImage) {
            imageRenderMethod.accept(simpleImage);
        }
    }

    public static class Builder {
        private Identifier identifier;
        private Pos startPos;
        private Pos stopPos;
        private float widthPercent;
        private float heightPercent;
        private Style style;

        public Builder() {
            this.identifier = new Identifier("customgui", "/textures/gui/empty_img.png");
            this.widthPercent = 0.5f;
            this.heightPercent = 1.0f;
            this.startPos = Pos.defaultPos();
            this.style = Style.defaultStyle();
        }

        public Builder identifier(Identifier imageIdentifier) {
            this.identifier = imageIdentifier;
            return this;
        }

        public Builder startPos(Pos startPos) {
            try {
                this.startPos = (Pos) startPos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder stopPos(Pos stopPos) {
            try {
                this.stopPos = (Pos) stopPos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder positions(Pos startPos, Pos stopPos) {
            startPos(startPos);
            stopPos(stopPos);
            return this;
        }

        public Builder positions(AbstractFrame frame) {
            startPos(frame.getStartPos());
            stopPos(frame.getStopPos());
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

        public Builder style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public SimpleImage build() {
            Pos stopPos = this.stopPos == null
                    ? Pos.builder()
                        .relativeCoords(
                             startPos.getXIndentPercent() + widthPercent,
                             startPos.getYIndentPercent() + heightPercent
                        )
                        .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                    : this.stopPos;
            SimpleImage image = new SimpleImage(startPos, stopPos, identifier);
            image.setStyle(style);
            return image;
        }
    }
}
