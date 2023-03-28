package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.CustomGUI;
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

    public static class Builder {
        private Pos startPos;
        private Pos stopPos;
        private Style style;
        private float widthPercent;
        private float heightPercent;

        public Builder() {
            this.startPos = Pos.defaultPos();
            this.style = Style.defaultStyle();
            this.widthPercent = style.getFont().getXSizePercent();
            this.heightPercent = style.getFont().getYSizePercent();
        }

        public Builder startPos(Pos pos) {
            try {
                this.startPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder stopPos(Pos pos) {
            try {
                this.stopPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
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

        public Builder positions(Pos startPos, Pos stopPos) {
            startPos(startPos);
            stopPos(stopPos);
            return this;
        }

        public Builder positions(AbstractFrame frame) {
            startPos(frame.getStartPos());
            stopPos(frame.getStopPos());
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

        public InputCursor build() {
            Pos stopPos = this.stopPos == null
                    ? Pos.builder()
                        .relativeCoords(
                                startPos.getXIndentPercent() + widthPercent,
                                startPos.getYIndentPercent() + heightPercent
                        )
                        .build(startPos.getXPercentValue(), startPos.getYPercentValue())
                    : this.stopPos;

            return new InputCursor(startPos, stopPos, style);
        }
    }
}
