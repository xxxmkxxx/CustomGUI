package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import net.minecraft.inventory.Inventory;

@Getter
public class SimpleSlot extends AbstractSlot {
    private final int backgroundColor;

    public SimpleSlot(int slotId, int index, Inventory inventory, Pos pos, Frame frame, int backgroundColor) {
        super(slotId, index, inventory, pos, frame);
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

        private void render(SimpleSlot node) {
            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    node.getMatrixStack(),
                    node.getFrame(),
                    node.getBackgroundColor()
            );
        }
    }
}
