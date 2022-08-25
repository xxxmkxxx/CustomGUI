package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

@Getter
@AllArgsConstructor
public abstract class AbstractSlot extends AbstractNode implements Slot {
    protected int index;
    protected final Inventory inventory;
    protected final Frame frame;

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
