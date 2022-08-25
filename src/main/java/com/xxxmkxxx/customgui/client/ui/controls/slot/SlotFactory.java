package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.geometry.Pos;

@FunctionalInterface
public interface SlotFactory<T extends AbstractSlot> {
    T create(int index, Pos pos);
}
