package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ImagedButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter
    private SimpleImage image;
    protected Runnable leftClickAction = () -> {};
    protected Runnable hoverAction = () -> {};
    protected Runnable resetHoverAction = () -> {};

    protected ImagedButton(Pos startPos, Pos stopPos, SimpleText text, SimpleImage image) {
        super(startPos, stopPos, text);
        this.image = image;
        updateIndents();
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        super.init(initMethod);
        image.init(initMethod);
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        image.scaling(window);
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
        text.initRenderer(type);
        image.initRenderer(type);
    }

    @Override
    public void update() {
        updateIndents();
    }

    @SuppressWarnings("DuplicatedCode")
    private void updateIndents() {
        float leftButtonMargin = style.getMargins().getLeft();
        float topButtonMargin = style.getMargins().getTop();
        float leftButtonPadding = style.getPaddings().getLeft();
        float topButtonPadding = style.getPaddings().getTop();
        float leftImageMargin = style.getMargins().getLeft();
        float topImageMargin = style.getMargins().getTop();
        float leftTextMargin = text.getStyle().getMargins().getLeft();
        float topTextMargin = text.getStyle().getMargins().getTop();

        frame.moveStartPos(leftButtonMargin, topButtonMargin);

        float tempXDistance = leftButtonMargin + leftButtonPadding + leftImageMargin;
        float tempYDistance = topButtonMargin + topButtonPadding + topImageMargin;

        image.getFrame().moveStartPos(tempXDistance, tempYDistance);
        image.getFrame().moveStopPos(tempXDistance, tempYDistance);

        tempXDistance += leftTextMargin;
        tempYDistance += topTextMargin;

        text.getFrame().moveStartPos(tempXDistance, tempYDistance);
        text.getFrame().moveStopPos(tempXDistance, tempYDistance);

        frame.moveStopPos(leftButtonMargin, topButtonMargin);
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

    public static class RendererFactory implements NodeRendererFactory<ImagedButton> {
        private final ParametrizedSelfDestructionMethod<ImagedButton> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<ImagedButton> renderBackgroundMethod = (button) -> {};

        @SuppressWarnings("DuplicatedCode")
        public RendererFactory() {
            initBackgroundMethod.setAction(button -> {
                renderBackgroundMethod = Background.chooseBackground(button.getStyle().getBackground().getType());
            });
        }

        @Override
        public NodeRenderer<ImagedButton> create(RendererType type) {
            return this::render;
        }

        private void render(ImagedButton imagedButton) {
            initBackgroundMethod.execute(imagedButton);

            renderBackgroundMethod.accept(imagedButton);

            imagedButton.getImage().getRenderer().render(imagedButton.getImage());
            imagedButton.getText().getRenderer().render(imagedButton.getText());
        }
    }

    public static class Builder extends AbstractNodeBuilder<ImagedButton> {
        private String text;
        private SimpleText textNode;
        private Identifier identifier;
        private SimpleImage image;

        public Builder() {
            super();
            this.text = "";
            this.identifier = new Identifier("customgui", "textures/gui/empty_img.png");
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

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder text(SimpleText text) {
            this.textNode = text;
            return this;
        }

        public Builder image(SimpleImage image) {
            this.image = image;
            return this;
        }

        public Builder image(Identifier identifier) {
            this.identifier = identifier;
            return this;
        }

        public ImagedButton build() {
            SimpleImage image = this.image == null ? SimpleImage.builder().identifier(identifier).build() : this.image;
            SimpleText text = this.textNode == null ? SimpleText.builder().text(this.text).build() : this.textNode;
            Pos stopPos = this.stopPos == null
                    ? Pos.builder()
                    .coords(
                            image.getFrame().getInitialStopPos().getX() + text.getFrame().getWidth(),
                            image.getFrame().getInitialStartPos().getY()
                    )
                    .build(startPos.getXPercentValue(), startPos.getXPercentValue())
                    : this.stopPos;

            ImagedButton imagedButton = new ImagedButton(startPos, stopPos, text, image);
            imagedButton.setStyle(style);
            return imagedButton;
        }
    }
}
