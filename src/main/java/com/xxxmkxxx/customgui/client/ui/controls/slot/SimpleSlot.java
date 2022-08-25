package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Getter
public class SimpleSlot extends AbstractSlot {
    private final int backgroundColor;

    private SimpleSlot(int index, Inventory inventory, Frame frame, int backgroundColor) {
        super(index, inventory, frame);
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

    public Identifier getItemAtlas(Item item) {
        Identifier itemIdentifier = Registry.ITEM.getId(item);
        Identifier itemAtlas = new Identifier(itemIdentifier.getNamespace(), "textures/gui/" + itemIdentifier.getPath());

        return itemAtlas;
    }

    @RequiredArgsConstructor
    public static class Factory implements SlotFactory<SimpleSlot> {
        private final int width;
        private final int height;
        private final int backgroundColor;
        private final Inventory inventory;

        @Override
        public SimpleSlot create(int index, Pos pos) {
            return new SimpleSlot(index, inventory, new Frame(pos, width, height, false), backgroundColor);
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleSlot> {
        @Override
        public NodeRenderer<SimpleSlot> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleSlot slot) {
            ItemStack itemStack = slot.getItemStack();

            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    slot.getMatrixStack(),
                    slot.getFrame(),
                    slot.getBackgroundColor()
            );

            if (itemStack == ItemStack.EMPTY)
                return;

            if (slot.isStandardItem()) {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        itemStack,
                        slot.getFrame()
                );
            } else {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        slot.getMatrixStack(),
                        slot.getFrame(),
                        slot.getItemAtlas(itemStack.getItem())
                );
            }

        }
    }
}
