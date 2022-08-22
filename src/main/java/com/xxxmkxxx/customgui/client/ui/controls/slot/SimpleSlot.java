package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Getter
public class SimpleSlot extends AbstractSlot {
    private final int backgroundColor;
    private Identifier itemAtlas = new Identifier("");

    public SimpleSlot(int slotId, ItemContainer itemContainer, Pos pos, Frame frame, int backgroundColor) {
        super(slotId, itemContainer, pos, frame);
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

    @Override
    public void onContainerUpdate() {
        Identifier itemId = Registry.ITEM.getId(itemContainer.getItemStack().getItem());

        this.itemAtlas = new Identifier(itemId.getNamespace(), "textures/gui/" + itemId.getPath() + ".png");
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

            if (slot.getItemContainer().isContainedStandardItem()) {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        slot.getItemContainer().getItemStack(),
                        slot.getFrame()
                );
            } else {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        slot.getMatrixStack(),
                        slot.getFrame(),
                        slot.getItemAtlas()
                );
            }

        }
    }
}
