package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class SquareSlotContainer<T extends AbstractSlot> extends RectangularSlotContainer<T> {
    protected SquareSlotContainer(Pos pos, SlotFactory<T> factory,int size, UnmodifiableLinearSlotContainer<T>[] rows) {
        super(pos, factory, size, size, rows);
    }

    public static class Builder<T extends AbstractSlot> extends RectangularSlotContainer.Builder<T> {
        public Builder<T> size(int size) {
            amountRows(size);
            rowSize(size);
            return this;
        }

        public SquareSlotContainer<T> build(SlotFactory<T> factory, int[][] indexes) {
            checkIndexes(amountRows, rowSize, indexes);
            return new SquareSlotContainer<>(pos, factory, amountRows, initRows(indexes, amountRows, rowSize, pos, factory));
        }
    }
}
