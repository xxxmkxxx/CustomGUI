package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractRowSlotContainer<T extends AbstractSlot> extends AbstractNode implements RowSlotContainer<T> {
    protected final int offset;
    protected final Pos pos;
    protected Pos currentSlotPos;
    protected final SlotFactory<T> factory;
}
