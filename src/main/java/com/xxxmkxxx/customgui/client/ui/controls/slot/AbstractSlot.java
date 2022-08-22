package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractSlot extends AbstractNode implements Slot, ItemContainerHandler {
    protected final int slotId;
    protected final ItemContainer itemContainer;
    protected final Pos pos;
    protected final Frame frame;
}
