package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.button.StandardButtonAnimations;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.ActionBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

@SuppressWarnings("unused")
public class SimpleButton extends AbstractButton implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    @SuppressWarnings("CodeBlock2Expr")
    protected SimpleButton(AbstractFrame frame, String name) {
        super(Text.of(name));
        this.frame = frame;
        this.leftClickAction = () -> {
            EventManager.sendAction(
                    rendererType,
                    ActionBuilder.of()
                            .addSimpleAnimation(
                                    "standard_left_click_button_animation",
                                    StandardButtonAnimations.LEFT_CLICK.getAnimation(),
                                    this,
                                    1
                            )
            );
        };
        this.hoverAction = () -> {
            EventManager.sendAction(
                    rendererType,
                    ActionBuilder.of()
                            .addStickyAnimation(
                                    "standard_hover_animation",
                                    StandardButtonAnimations.HOVER.getAnimation(),
                                    this,
                                    1
                            )
            );
        };
        this.resetHoverAction = () -> {
            EventManager.sendAction(
                    rendererType,
                    ActionBuilder.of()
                            .deleteStickyAnimation("standard_hover_animation")
            );
        };
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

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new RendererFactory().create(type);
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements SimpleBuilder<SimpleButton> {
        private String buttonName = "";
        private Pos startPos = new Pos(0, 0);
        private Style style = new Style();

        public Builder() {}

        public Builder name(String buttonName) {
            this.buttonName = buttonName;
            return this;
        }

        public Builder startPos(Pos pos) {
            this.startPos = pos;
            return this;
        }

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        @Override
        public SimpleButton build() {
            DynamicFrame frame = new DynamicFrame(startPos, 0, 0, false);
            SimpleButton button = new SimpleButton(frame, buttonName);
            button.setStyle(style);

            return button;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleButton> {
        private final ParametrizedSelfDestructionMethod<SimpleButton> initFrameMethod = new ParametrizedSelfDestructionMethod<>();

        public RendererFactory() {
            initFrameMethod.setAction(button -> {
                TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                int textWidthWithIndent = textRenderer.getWidth(button.getName()) + button.getStyle().getIndent().getRight();
                int textHeightWithIndent = textRenderer.fontHeight + button.getStyle().getIndent().getBottom();

                Pos stopPos = new Pos(
                        button.getFrame().getStartPos().x() + textWidthWithIndent,
                        button.getFrame().getStartPos().y() + textHeightWithIndent
                );

                ((DynamicFrame)button.getFrame()).setStopPos(stopPos);
            });
        }

        @Override
        public NodeRenderer<SimpleButton> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleButton button) {
            initFrameMethod.execute(button);

            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    button.getMatrixStack(),
                    button.getFrame(),
                    0xA02071c7
            );

            CustomGUIClient.NODE_DRAWABLE_HELPER.drawText(
                    button.getMatrixStack(),
                    button.getName(),
                    button.getFrame().getStartPos().x() + button.getStyle().getIndent().getLeft(),
                    button.getFrame().getStartPos().y() + button.getStyle().getIndent().getTop(),
                    0xFF000000
            );
        }
    }
}
