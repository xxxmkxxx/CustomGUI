package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;

public class SimpleButton extends AbstractButton {
    @Override
    public void initRenderer(RendererType type) {
        renderer = new RendererFactory().create(type);
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleButton> {
        @Override
        public NodeRenderer<SimpleButton> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleButton button) {

        }
    }
}
