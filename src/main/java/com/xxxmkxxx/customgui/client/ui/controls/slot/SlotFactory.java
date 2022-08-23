package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.geometry.Frame;

@FunctionalInterface
public interface SlotFactory<T extends AbstractSlot> {
    T create(Frame frame, ItemContainer itemContainer);
}
