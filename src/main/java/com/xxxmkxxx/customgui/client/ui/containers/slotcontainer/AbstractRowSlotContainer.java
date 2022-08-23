package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractRowSlotContainer<T extends AbstractSlot> extends AbstractNode implements RowSlotContainer<T> {
    protected final int offset;
}
