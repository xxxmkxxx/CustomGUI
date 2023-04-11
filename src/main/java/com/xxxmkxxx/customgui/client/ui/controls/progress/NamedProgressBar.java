package com.xxxmkxxx.customgui.client.ui.controls.progress;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import com.xxxmkxxx.customgui.client.ui.shapes.rectangle.SimpleRectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public class NamedProgressBar extends AbstractProgressBar {
    private SimpleText progressText;
    private SimpleRectangle progressRectangle;
    @Setter
    private ViewMod viewMod;
    @Setter
    private String postfix;

    protected NamedProgressBar(Pos startPos, Pos stopPos, float progressPercent, float progressPercentValue, SimpleText progressText, SimpleRectangle processRectangle, ViewMod viewMod, String postfix, Style style) {
        super(startPos, stopPos, progressPercent, progressPercentValue, style);
        this.progressText = progressText;
        this.progressRectangle = processRectangle;
        this.viewMod = viewMod;
        this.postfix = postfix;
        updateIndents();
    }

    @Override
    public void moveProgress(float percent) {
        super.moveProgress(percent);
        progressText.setText(chooseText(viewMod) + postfix);
        progressRectangle.getFrame().moveStopPos(progressPercent * progressPercentValue, 0);
    }

    private String chooseText(ViewMod viewMod) {
        String text = "";

        switch(viewMod) {
            case BY_VALUE -> text = String.valueOf(progressPercent * progressPercentValue);
            case PERCENTAGE -> text = String.valueOf(progressPercent);
        }

        return text;
    }

    @Override
    public void update() {
        updateIndents();
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new RendererFactory().create(type);
        progressText.initRenderer(type);
        progressRectangle.initRenderer(type);
    }

    @Override
    public void hide() {
        super.hide();
        progressText.hide();
        progressRectangle.hide();
    }

    @Override
    public void display() {
        super.display();
        progressText.display();
        progressRectangle.display();
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        super.init(initMethod);
        progressText.init(initMethod);
        progressRectangle.init(initMethod);
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        progressText.scaling(window);
        progressRectangle.scaling(window);
    }

    private void updateIndents() {
        float leftProgressBarMargin = style.getMargins().getLeft();
        float topProgressBarMargin = style.getMargins().getTop();
        float leftProgressBarPadding = style.getPaddings().getLeft();
        float topProgressBarPadding = style.getPaddings().getTop();
        float leftProgressTextMargin = progressText.getStyle().getMargins().getLeft();
        float topProgressTextMargin = progressText.getStyle().getMargins().getTop();

        frame.moveStartPos(leftProgressBarMargin, topProgressBarMargin);

        float tempXDistance = leftProgressBarMargin + leftProgressTextMargin + leftProgressBarPadding;
        float tempYDistance = topProgressBarMargin + topProgressTextMargin + topProgressBarPadding;

        progressText.getFrame().moveStartPos(tempXDistance, tempYDistance);
        progressText.getFrame().moveStopPos(tempXDistance, tempYDistance);

        frame.moveStopPos(leftProgressBarMargin, topProgressBarMargin);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class RendererFactory implements NodeRendererFactory<NamedProgressBar> {
        @Override
        public NodeRenderer<NamedProgressBar> create(RendererType type) {
            return this::render;
        }

        private void render(NamedProgressBar namedProgressBar) {
            CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                    namedProgressBar.getStyle().getMatrixStack(),
                    namedProgressBar.getFrame(),
                    namedProgressBar.getStyle().getHexBackgroundColor()
            );

            namedProgressBar.getProgressRectangle().getState().execute(namedProgressBar.getProgressRectangle(), namedProgressBar.getProgressRectangle().getRenderer());
            namedProgressBar.getProgressText().getState().execute(namedProgressBar.getProgressText(), namedProgressBar.getProgressText().getRenderer());
        }
    }

    public static class Builder extends AbstractNodeBuilder<NamedProgressBar> {
        private float progressPercent;
        private float progressPercentValue;
        private SimpleText progressTextNode;
        private SimpleRectangle progressRectangle;
        private String postfix;
        private ViewMod viewMod;

        public Builder() {
            this.progressPercent = 50;
            this.postfix = "";
            this.viewMod = ViewMod.PERCENTAGE;
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
        public Builder sizes(float width, float height) {
            return (Builder) super.sizes(width, height);
        }

        @Override
        public Builder sizesPercent(float widthPercent, float heightPercent) {
            return (Builder) super.sizesPercent(widthPercent, heightPercent);
        }

        @Override
        public Builder style(Style style) {
            return (Builder) super.style(style);
        }


        public Builder progressText(SimpleText progressText) {
            this.progressTextNode = progressText;
            return this;
        }

        public Builder progressPercent(float progressPercent) {
            this.progressPercent = progressPercent;
            return this;
        }

        public Builder progressPercentValue(float progressPercentValue) {
            this.progressPercentValue = progressPercentValue;
            return this;
        }

        public Builder progressRectangle(SimpleRectangle progressRectangle) {
            this.progressRectangle = progressRectangle;
            return this;
        }

        public Builder postfix(String postfix) {
            this.postfix = postfix;
            return this;
        }

        public Builder viewMod(ViewMod viewMod) {
            this.viewMod = viewMod;
            return this;
        }

        private String chooseText(ViewMod viewMod) {
            String text = "";

            switch(viewMod) {
                case BY_VALUE -> text = String.valueOf(progressPercent * progressPercentValue);
                case PERCENTAGE -> text = String.valueOf(progressPercent);
            }

            return text;
        }

        @Override
        public NamedProgressBar build() {
            Pos stopPos = createStopPos();

            SimpleRectangle progressRectangle = this.progressRectangle == null
                    ? SimpleRectangle.builder()
                        .positions(
                                startPos,
                                Pos.builder()
                                        .coords(
                                            startPos.getX() + (stopPos.getX() - startPos.getX()) / 100 * progressPercent,
                                            stopPos.getY()
                                        )
                                        .proportionBy(startPos.getProportionBy())
                                        .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                        )
                    .style(style)
                        .build()
                    : this.progressRectangle;

            SimpleText progressTextNode = this.progressTextNode == null
                    ? SimpleText.builder().style(style).text(chooseText(viewMod)).startPos(startPos).build()
                    : this.progressTextNode;

            return new NamedProgressBar(startPos, stopPos, progressPercent, progressPercentValue, progressTextNode, progressRectangle, viewMod, postfix, style);
        }
    }
}
