package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

@FunctionalInterface
public interface SlotFactory<T extends AbstractSlot> {
    T create(int index, Pos pos);
}
