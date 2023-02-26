package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

@Getter
public abstract class AbstractSlot extends AbstractNode implements Slot {
    protected int index;
    protected final Inventory inventory;

    public AbstractSlot(Pos startPos, int width, int height, int index, Inventory inventory) {
        this.index = index;
        this.inventory = inventory;

        //gag
        this.frame = SimpleFrame.builder().startPos(startPos).widthPercent(0.0).heightPercent(0.0).build();
    }

    public ItemStack getItemStack() {
        return inventory.getStack(index);
    }

    public boolean isStandardItem() {
        return isStandardItem(getItemStack().getItem());
    }

    public boolean isStandardItem(Item item) {
        return Registry.ITEM.getId(item).getNamespace().equals("minecraft");
    }
}
