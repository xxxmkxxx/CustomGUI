package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

@Getter
public abstract class AbstractSlot extends AbstractNode implements Slot {
    protected int index;
    protected final Inventory inventory;

    public AbstractSlot(Pos startPos, Pos stopPos, int index, Inventory inventory, Style style) {
        this.index = index;
        this.inventory = inventory;
        this.style = style;
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
    }

    public ItemStack getItemStack() {
        return inventory.getStack(index);
    }
}
