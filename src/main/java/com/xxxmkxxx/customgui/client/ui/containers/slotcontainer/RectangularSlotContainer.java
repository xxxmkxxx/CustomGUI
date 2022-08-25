package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

@Getter
public class RectangularSlotContainer <T extends AbstractSlot> extends AbstractMultiRowSlotContainer<T> {
    private final int rowSize;
    private final int amountRows;
    private final UnmodifiableLinearSlotContainer<T>[] rows;

    protected RectangularSlotContainer(int offset, Pos pos, SlotFactory<T> factory, int rowSize, int amountRows, UnmodifiableLinearSlotContainer<T>[] rows) {
        super(offset, pos, factory);
        this.rowSize = rowSize;
        this.amountRows = amountRows;
        this.rows = rows;
    }

    @Override
    public void initRenderer(RendererType type) {
        Arrays.stream(rows).forEach(container -> container.initRenderer(type));

        renderer = new SquareSlotContainer.RendererFactory<T>().create(type);
    }

    @Override
    public AbstractRowSlotContainer<T> getRow(int index) {
        Validator.checkArrayIndex(index, rows);

        return rows[index];
    }

    @Override
    public T getSlot(int rowIndex, int slotIndex) {
        Validator.checkArrayIndex(rowIndex, rows);

        return rows[rowIndex].getSlot(slotIndex);
    }

    @Override
    public void setSlot(int rowIndex, int slotIndex, T slot) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SquareSlotContainer cannot be changed!");
    }

    public static class Builder<T extends AbstractSlot> {
        protected final int[][] indexes;
        protected int rowSize = 1;
        protected int amountRows = 1;
        protected int offset = 1;

        public Builder(int[][] indexes) {
            this.indexes = indexes;
        }

        public Builder<T> rowSize(int rowSize) {
            Validator.checkArraySize(rowSize);
            this.rowSize = rowSize;
            return this;
        }

        public Builder<T> amountRows(int amountRows) {
            Validator.checkArraySize(amountRows);
            this.amountRows = amountRows;
            return this;
        }

        public Builder<T> offset(int offset) {
            this.offset = offset;
            return this;
        }

        public RectangularSlotContainer<T> build(Pos pos, SlotFactory<T> factory) {
            checkIndexes(amountRows, rowSize, indexes);
            return new RectangularSlotContainer<>(offset, pos, factory, amountRows, rowSize, initRows(indexes, amountRows, rowSize, offset, pos, factory));
        }

        protected void checkIndexes(int amountRows, int rowSize, int[][] indexes) {
            if (indexes.length != amountRows)
                throw new RuntimeException("Array size - " + indexes.length + " not equals size - " + amountRows);

            for (int i = 0; i < amountRows; i++) {
                if (indexes[i].length != rowSize)
                    throw new RuntimeException("Array size - " + indexes[i].length + "not equals size - " + rowSize);
            }
        }

        @SuppressWarnings("unchecked")
        protected UnmodifiableLinearSlotContainer<T>[] initRows(int[][] indexes, int amountRows, int rowSize, int offset, Pos pos, SlotFactory<T> factory) {
            final UnmodifiableLinearSlotContainer<T>[] result = new UnmodifiableLinearSlotContainer[amountRows];
            Pos currentPos = pos;

            for (int i = 0; i < amountRows; i++) {
                UnmodifiableLinearSlotContainer<T> container = result[i] = new UnmodifiableLinearSlotContainer.Builder<T>(indexes[i])
                        .size(rowSize)
                        .offset(offset)
                        .build(currentPos, factory);

                currentPos = new Pos(pos.x(), container.getSlot(0).getFrame().getStopPos().y() + offset);
            }

            return result;
        }
    }

    public static class RendererFactory<T extends AbstractSlot> implements NodeRendererFactory<RectangularSlotContainer<T>> {
        @Override
        public NodeRenderer<RectangularSlotContainer<T>> create(RendererType type) {
            return this::render;
        }

        @SuppressWarnings("unchecked")
        private void render(RectangularSlotContainer<T> container) {
            Arrays.stream(container.getRows()).forEach(row -> row.getRenderer().render(row));
        }
    }
}
