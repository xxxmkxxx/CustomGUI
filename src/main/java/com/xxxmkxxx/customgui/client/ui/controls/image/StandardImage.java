package com.xxxmkxxx.customgui.client.ui.controls.image;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class StandardImage extends AbstractImage implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter @Setter
    private ItemStack itemStack;
    private Runnable leftClickAction = () -> {};
    private Runnable hoverAction = () -> {};
    private Runnable  resetHoverAction = () -> {};

    public StandardImage(Pos startPos, Pos stopPos, ItemStack itemStack, Identifier imageIdentifier) {
        super(startPos, stopPos, imageIdentifier);
        this.itemStack = itemStack;
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

    public static class RendererFactory implements NodeRendererFactory<StandardImage> {
        private Consumer<StandardImage> imageRenderMethod;

        public RendererFactory() {
            imageRenderMethod = standardImage -> {
                CustomGUI.NODE_DRAWABLE_HELPER.drawTexture(
                        standardImage.getItemStack(),
                        standardImage.getFrame()
                );
            };
        }

        @Override
        public NodeRenderer<StandardImage> create(RendererType type) {
            return this::render;
        }

        private void render(StandardImage standardImage) {
            imageRenderMethod.accept(standardImage);
        }
    }

    public static class Builder extends AbstractNodeBuilder<StandardImage> {
        private float widthPercent;
        private float heightPercent;
        private ItemStack itemStack;

        public Builder() {
            super();
            this.widthPercent = 0.5f;
            this.heightPercent = 1.0f;
            this.itemStack = ItemStack.EMPTY;
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

        public Builder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
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

        public StandardImage build() {
            Pos stopPos = this.stopPos == null
                    ? Pos.builder()
                    .relativeCoords(
                            startPos.getXIndentPercent() + widthPercent,
                            startPos.getYIndentPercent() + heightPercent
                    )
                    .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                    : this.stopPos;
            StandardImage image = new StandardImage(startPos, stopPos, itemStack, Registry.ITEM.getId(itemStack.getItem()));
            image.setStyle(style);
            return image;
        }
    }
}
