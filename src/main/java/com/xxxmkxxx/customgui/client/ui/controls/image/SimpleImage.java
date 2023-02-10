package com.xxxmkxxx.customgui.client.ui.controls.image;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class SimpleImage extends AbstractImage implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable  resetHoverAction = () -> {};

    protected SimpleImage(Pos startPos, int width, int height, Identifier imageIdentifier) {
        super(startPos, width, height, imageIdentifier);
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

    public static class RendererFactory implements NodeRendererFactory<SimpleImage> {
        private final ParametrizedSelfDestructionMethod<SimpleImage> initImageRendererMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<SimpleImage> imageRenderMethod;

        public RendererFactory() {
            initImageRendererMethod.setAction((image) -> {
                if (image.isStandard()) {
                    imageRenderMethod = simpleImage -> {};
                }
                else {
                    imageRenderMethod = simpleImage -> {
                        CustomGUI.NODE_DRAWABLE_HELPER.drawTexture(
                                simpleImage.getStyle().getMatrixStack(),
                                simpleImage.getFrame(),
                                simpleImage.getImageIdentifier()
                        );
                    };
                }
            });
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
        private Identifier imageIdentifier = new Identifier("modid");
        private int width = 10;
        private int height = 10;
        private Pos startPos = Pos.defaultPos();
        private Style style = Style.defaultStyle();

        public Builder identifier(Identifier imageIdentifier) {
            this.imageIdentifier = imageIdentifier;
            return this;
        }

        public Builder startPos(Pos pos) {
            this.startPos = pos;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
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
            SimpleImage image = new SimpleImage(startPos, width, height, imageIdentifier);
            image.setStyle(style);
            return image;
        }
    }
}
