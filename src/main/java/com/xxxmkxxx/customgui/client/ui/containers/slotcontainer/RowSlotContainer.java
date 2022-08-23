package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;

public interface RowSlotContainer<T extends AbstractSlot> {
    void addSlot(T slot);
    T getSlot(int index);
}
