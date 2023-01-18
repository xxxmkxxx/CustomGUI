package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.Section;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.controls.image.AbstractImage;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Function;

public class ImagedButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter
    private AbstractImage image;
    protected Runnable leftClickAction = () -> {};
    protected Runnable hoverAction = () -> {};
    protected Runnable resetHoverAction = () -> {};

    private ImagedButton(Pos pos, Text name, AbstractImage image) {
        super(name);
        this.frame = new DynamicFrame(pos, 1, 1, false);
        this.image = image;
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
        this.getImage().initRenderer(type);
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
        private final ParametrizedSelfDestructionMethod<ImagedButton> initFrameMethod = new ParametrizedSelfDestructionMethod<>();

        public RendererFactory() {
            initFrameMethod.setAction(button -> {
                int textWidthWithIndent = Utils.getTextWidth(button.getName()) + button.getStyle().getIndent().getRight();
                int textHeightWithIndent = Utils.getTextHeight() + button.getStyle().getIndent().getBottom();
                int imageWidth = button.getImage().getFrame().getWidth();
                int imageHeight = button.getImage().getFrame().getHeight();
                int stopPosX = imageWidth > textWidthWithIndent ? imageWidth : textWidthWithIndent;
                int stopPosY = imageHeight > textHeightWithIndent ? imageHeight : textHeightWithIndent;

                Pos stopPos = new Pos(
                        button.getFrame().getStartPos().x() + stopPosX,
                        button.getFrame().getStartPos().y() + stopPosY
                );

                ((DynamicFrame)button.getFrame()).setStopPos(stopPos);
            });
        }

        @Override
        public NodeRenderer<ImagedButton> create(RendererType type) {
            return this::render;
        }

        private void render(ImagedButton imagedButton) {
            initFrameMethod.execute(imagedButton);

            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    imagedButton.getMatrixStack(),
                    imagedButton.getFrame(),
                    0xA02071c7
            );

            CustomGUIClient.NODE_DRAWABLE_HELPER.drawText(
                    imagedButton.getMatrixStack(),
                    imagedButton.getName(),
                    imagedButton.getFrame().getStartPos().x() + imagedButton.getStyle().getIndent().getLeft(),
                    imagedButton.getFrame().getStartPos().y() + imagedButton.getStyle().getIndent().getTop(),
                    0xFF000000
            );

            imagedButton.getImage().getRenderer().render(imagedButton.getImage());
        }
    }

    public static class Builder {
        private Pos pos = new Pos(5, 5);
        private Text text = Text.of("");
        private AbstractImage image;

        public Builder pos(Pos pos) {
            this.pos = pos;
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

        public Builder image(AbstractImage image) {
            this.image = image;
            return this;
        }

        public Builder image(Identifier imageIdentifier, AbstractFrame frame) {
            this.image = SimpleImage.builder().identifier(imageIdentifier).frame(frame).build();
            return this;
        }

        public ImagedButton build() {
            return new ImagedButton(pos, text, image);
        }
    }
}
