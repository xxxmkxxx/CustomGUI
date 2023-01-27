package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.image.AbstractImage;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ImagedButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter
    private AbstractImage image;
    protected Runnable leftClickAction = () -> {};
    protected Runnable hoverAction = () -> {};
    protected Runnable resetHoverAction = () -> {};

    private ImagedButton(Pos startPos, Text text, AbstractImage image, Style style) {
        super(startPos, SimpleText.builder().pos(startPos).text(text).build());
        this.style = style;
        this.image = image;
        initFrame(image);
    }

    private void initFrame(AbstractImage image) {
        this.frame.setStopPos(new Pos(
                frame.getStopPos().x() + image.getFrame().getWidth() + image.getStyle().getIndent().getRight(),
                frame.getStopPos().y() + image.getFrame().getHeight() + image.getStyle().getIndent().getBottom()
        ));
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        super.init(initMethod);
        image.init(initMethod);
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

    public static class Builder {
        private Pos startPos = Pos.DEFAULT_POS;
        private Text text = Text.of("button");
        private Style style = Style.DEFAULT_STYLE;
        private AbstractImage image;

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        public Builder pos(Pos startPos) {
            this.startPos = startPos;
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

        public ImagedButton build() {
            return new ImagedButton(startPos, text, image, style);
        }
    }
}
