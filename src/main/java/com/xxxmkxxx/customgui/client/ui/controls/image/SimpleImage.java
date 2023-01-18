package com.xxxmkxxx.customgui.client.ui.controls.image;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import net.minecraft.util.Identifier;

public class SimpleImage extends AbstractImage implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable  resetHoverAction = () -> {};

    private SimpleImage(Identifier imageIdentifier, AbstractFrame frame) {
        this.imageIdentifier = imageIdentifier;
        this.frame = frame;
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

        @Override
        public NodeRenderer<SimpleImage> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleImage simpleImage) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                    simpleImage.getMatrixStack(),
                    simpleImage.getFrame(),
                    simpleImage.getImageIdentifier()
            );
        }
    }

    public static class Builder {
        private Identifier imageIdentifier = new Identifier("modid");
        private AbstractFrame frame = new DynamicFrame(5, 5, 10, 10, false);

        public Builder identifier(Identifier imageIdentifier) {
            this.imageIdentifier = imageIdentifier;
            return this;
        }

        public Builder frame(AbstractFrame frame) {
            this.frame = frame;
            return this;
        }

        public SimpleImage build() {
            return new SimpleImage(imageIdentifier, frame);
        }
    }
}
