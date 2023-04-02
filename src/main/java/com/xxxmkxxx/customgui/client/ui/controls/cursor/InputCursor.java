package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.field.NoneExpandableInputField;

public class InputCursor extends AbstractCursor {
    protected InputCursor(Pos startPos, Pos stopPos, Style style) {
        super(startPos,stopPos, style);
        updateIndents();
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
        float leftCursorMargin = style.getMargins().getLeft();
        float topCursorMargin = style.getMargins().getTop();

        frame.moveStartPos(leftCursorMargin, topCursorMargin);
        frame.moveStopPos(leftCursorMargin, topCursorMargin);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class RendererFactory implements NodeRendererFactory<InputCursor> {
        @Override
        public NodeRenderer<InputCursor> create(RendererType type) {
            return this::render;
        }

        private void render(InputCursor inputCursor) {
            CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                    inputCursor.getStyle().getMatrixStack(),
                    inputCursor.getFrame(),
                    inputCursor.getStyle().getHexColor()
            );
        }
    }

    public static class Builder extends AbstractNodeBuilder<InputCursor> {
        private float widthPercent;
        private float heightPercent;

        public Builder() {
            super();
            this.widthPercent = style.getFont().getXSizePercent();
            this.heightPercent = style.getFont().getYSizePercent();
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

        public Builder widthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
            return this;
        }

        public Builder heightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
            return this;
        }

        public InputCursor build() {
            return build(() -> {
                Pos stopPos = this.stopPos == null
                        ? Pos.builder()
                        .relativeCoords(
                                startPos.getXIndentPercent() + widthPercent,
                                startPos.getYIndentPercent() + heightPercent
                        )
                        .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                        : this.stopPos;

                return new InputCursor(startPos, stopPos, style);
            });
        }
    }
}
