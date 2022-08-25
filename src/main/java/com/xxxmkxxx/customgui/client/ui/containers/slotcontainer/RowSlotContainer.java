package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;

import javax.naming.OperationNotSupportedException;

public interface RowSlotContainer<T extends AbstractSlot> {
    void addSlot(int index) throws OperationNotSupportedException;
    void setSlot(int index, T slot) throws OperationNotSupportedException;
    T getSlot(int index);
}
