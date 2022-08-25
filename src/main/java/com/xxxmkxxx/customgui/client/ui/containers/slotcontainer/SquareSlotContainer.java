package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

@Getter
public class SquareSlotContainer<T extends AbstractSlot> extends RectangularSlotContainer<T> {
    protected SquareSlotContainer(int offset, Pos pos, SlotFactory<T> factory,int size, UnmodifiableLinearSlotContainer<T>[] rows) {
        super(offset, pos, factory, size, size, rows);
    }

    public static class Builder<T extends AbstractSlot> extends RectangularSlotContainer.Builder<T> {
        public Builder(int[][] indexes) {
            super(indexes);
        }

        public Builder<T> size(int size) {
            amountRows(size);
            rowSize(size);
            return this;
        }

        public SquareSlotContainer<T> build(Pos pos, SlotFactory<T> factory) {
            checkIndexes(amountRows, rowSize, indexes);
            return new SquareSlotContainer<>(offset, pos, factory, amountRows, initRows(indexes, amountRows, rowSize, offset, pos, factory));
        }
    }
}