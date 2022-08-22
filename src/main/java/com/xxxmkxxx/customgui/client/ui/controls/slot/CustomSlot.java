package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;

@Getter
public class SimpleSlot extends AbstractSlot {
    private final int backgroundColor;

    public SimpleSlot(int slotId, ItemContainer itemContainer, Pos pos, Frame frame, boolean isCustomAtlas, int backgroundColor) {
        super(slotId, itemContainer, pos, frame, isCustomAtlas);
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void hide() {
        this.state = States.HIDED;
    }

    @Override
    public void display() {
        this.state = States.DISPLAYED;
    }

    @Override
    public void initRenderer(RendererType type) {
        this.renderer = new RendererFactory().create(type);
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleSlot> {
        @Override
        public NodeRenderer<SimpleSlot> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleSlot slot) {
            slot.itemContainer.update();

            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    slot.getMatrixStack(),
                    slot.getFrame(),
                    slot.getBackgroundColor()
            );

            if (slot.isCustomItemAtlas()) {
                renderCustomItemAtlas(slot);
            } else {
                renderStandardItemAtlas(slot);
            }
        }

        private void renderStandardItemAtlas(SimpleSlot slot) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                    slot.getItemContainer().getItemStack(),
                    slot.getFrame()
            );
        }

        private void renderCustomItemAtlas(SimpleSlot slot) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                    slot.getMatrixStack(),
                    slot.getFrame(),
                    slot.getItemContainer().getItemAtlas()
            );
        }
    }
}
