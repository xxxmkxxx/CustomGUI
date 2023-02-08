package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public class InputCursor extends AbstractCursor {
    protected InputCursor(Pos pos, int width, int height) {
        super(pos, width, height);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
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
        private Pos pos = Pos.DEFAULT_POS;
        private Style style = Style.DEFAULT_STYLE;
        private int width = 1;
        private int height = 7;

        public Builder pos(Pos pos) {
            this.pos = pos;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder style(Style style) {
            this.style = style;
            return this;
        }

        public InputCursor build() {
            InputCursor cursor = new InputCursor(pos, width, height);
            cursor.setStyle(style);
            return cursor;
        }
    }
}
