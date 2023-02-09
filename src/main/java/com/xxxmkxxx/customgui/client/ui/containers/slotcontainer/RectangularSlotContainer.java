package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;
import org.lwjgl.system.CallbackI;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@SuppressWarnings("unused")
public class RectangularSlotContainer <T extends AbstractSlot> extends AbstractMultiRowSlotContainer<T> {
    private final int rowSize;
    private final int amountRows;
    private final UnmodifiableLinearSlotContainer<T>[] rows;

    protected RectangularSlotContainer(int offset, Pos pos, SlotFactory<T> factory, int rowSize, int amountRows, UnmodifiableLinearSlotContainer<T>[] rows) {
        super(offset, factory);
        this.rowSize = rowSize;
        this.amountRows = amountRows;
        this.rows = rows;
        UnmodifiableLinearSlotContainer<T> container = rows[rows.length - 1];
        this.frame = new SimpleFrame(pos, container.getSlot(container.getSize() - 1).getFrame().getStopPos());
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new SquareSlotContainer.RendererFactory<T>().create(type);
        Arrays.stream(rows).forEach(container -> container.initRenderer(type));
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);

        for (int i = 0; i < amountRows; i++) {
            rows[i].initSection(initMethod);
        }
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        for (int i = 0; i < amountRows; i++) {
            rows[i].init(initMethod);
        }
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

    public static <S extends AbstractSlot> Builder<S> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends AbstractSlot> {
        protected Pos pos = Pos.DEFAULT_POS;
        protected int rowSize = 1;
        protected int amountRows = 1;
        protected int offset = 1;

        public Builder<T> pos(Pos pos) {
            this.pos = pos;
            return this;
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

        public RectangularSlotContainer<T> build(SlotFactory<T> factory, int[][] indexes) {
            checkIndexes(amountRows, rowSize, indexes);
            return new RectangularSlotContainer<>(offset, pos, factory, rowSize, amountRows, initRows(indexes, amountRows, rowSize, offset, pos, factory));
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
            Pos currentPos = new Pos(pos.getX(), pos.getY());

            for (int i = 0; i < amountRows; i++) {
                UnmodifiableLinearSlotContainer<T> container = result[i] = new UnmodifiableLinearSlotContainer.Builder<>(currentPos, factory, indexes[i])
                        .size(rowSize)
                        .offset(offset)
                        .build();

                currentPos = new Pos(pos.getX(), currentPos.getY() + container.getSlot(0).getFrame().getHeight());
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
            Arrays.stream(container.getRows()).forEach(row -> row.getState().execute(row, row.getRenderer()));
        }
    }
}
