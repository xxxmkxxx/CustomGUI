package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public class InputCursor extends AbstractCursor {
    protected InputCursor(Pos pos, float width, float height) {
        super(pos, width, height);
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
        private Pos pos = Pos.defaultPos();
        private Style style = Style.defaultStyle();
        private int width = 1;
        private int height = 7;

        public Builder pos(Pos pos) {
            try {
                this.pos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        //gag
        public Builder width(float width) {
            return this;
        }

        //gag
        public Builder height(float height) {
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
            InputCursor cursor = new InputCursor(pos, width, height);
            cursor.setStyle(style);
            return cursor;
        }
    }
}
