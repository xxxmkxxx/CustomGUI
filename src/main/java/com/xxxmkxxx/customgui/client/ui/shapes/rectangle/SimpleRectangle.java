package com.xxxmkxxx.customgui.client.ui.shapes.rectangle;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNodeBuilder;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;

public class SimpleRectangle extends AbstractRectangle {
    protected SimpleRectangle(AbstractFrame frame, Style style) {
        this.frame = frame;
        this.style = style;
    }

    @Override
    public void update() {

    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new RendererFactory().create(type);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleRectangle> {
        @Override
        public NodeRenderer<SimpleRectangle> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleRectangle simpleRectangle) {
            CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                    simpleRectangle.getStyle().getMatrixStack(),
                    simpleRectangle.getFrame(),
                    simpleRectangle.getStyle().getHexBackgroundColor()
            );
        }
    }

    public static class Builder extends AbstractNodeBuilder<SimpleRectangle> {
        @Override
        public SimpleRectangle build() {
            return new SimpleRectangle(SimpleFrame.builder().positions(startPos, stopPos).build(), style);
        }
    }
}
