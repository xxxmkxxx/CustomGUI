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
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import lombok.Getter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ImagedButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    @Getter
    private AbstractImage image;
    protected Runnable leftClickAction = () -> {};
    protected Runnable hoverAction = () -> {};
    protected Runnable resetHoverAction = () -> {};

    private ImagedButton(Pos startPos, Text text, AbstractImage image) {
        super(startPos, text.getString());
        this.image = image;
        initFrame(image);
    }

    private void initFrame(AbstractImage image) {
        this.frame.moveStopPos(new Pos(
                frame.getStopPos().getX() + image.getFrame().getWidth() + image.getStyle().getIndent().getRight(),
                frame.getStopPos().getY() + image.getFrame().getHeight() + image.getStyle().getIndent().getBottom()
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
        private Pos pos = Pos.defaultPos();
        private Text text = Text.of("button");
        private Style style = Style.defaultStyle();
        private SimpleImage image = SimpleImage.builder().identifier(new Identifier("customgui", "empty_img.png")).build();

        public Builder style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder pos(Pos startPos) {
            try {
                this.pos = (Pos) startPos.clone();
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

        public Builder image(SimpleImage image) {
            this.image = SimpleImage.builder()
                    .pos(pos)
                    .identifier(image.getImageIdentifier())
                    .height(image.getFrame().getHeight())
                    .width(image.getFrame().getWidth())
                    .style(image.getStyle())
                    .build();
            return this;
        }

        public Builder image(Identifier identifier) {
            this.image = SimpleImage.builder()
                    .identifier(identifier)
                    .pos(pos)
                    .build();
            return this;
        }

        public ImagedButton build() {
            ImagedButton imagedButton = new ImagedButton(pos, text, image);
            imagedButton.setStyle(style);
            return imagedButton;
        }
    }
}
