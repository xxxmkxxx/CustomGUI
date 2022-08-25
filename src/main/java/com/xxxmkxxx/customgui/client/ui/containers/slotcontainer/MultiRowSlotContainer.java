package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;

import javax.naming.OperationNotSupportedException;

public interface MultiRowSlotContainer<T extends AbstractSlot> {
    AbstractRowSlotContainer<T> getRow(int index);
    T getSlot(int rowIndex, int slotIndex);
    void setSlot(int rowIndex, int slotIndex, T slot) throws OperationNotSupportedException;
}
