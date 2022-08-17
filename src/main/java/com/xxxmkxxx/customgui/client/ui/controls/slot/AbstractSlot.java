package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.inventory.Inventory;


@Getter
@AllArgsConstructor
public abstract class AbstractSlot extends AbstractNode implements Slot {
    protected final int slotId;
    protected final int index;
    protected final Inventory inventory;
    protected final Pos pos;
    protected final Frame frame;
}
