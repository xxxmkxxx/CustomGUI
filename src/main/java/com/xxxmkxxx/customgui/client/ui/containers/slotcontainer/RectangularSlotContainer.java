package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

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

    protected RectangularSlotContainer(Pos pos, SlotFactory<T> factory, int rowSize, int amountRows, UnmodifiableLinearSlotContainer<T>[] rows) {
        super(factory);
        this.rowSize = rowSize;
        this.amountRows = amountRows;
        this.rows = rows;
        UnmodifiableLinearSlotContainer<T> container = rows[rows.length - 1];
        this.frame = SimpleFrame.builder()
                .positions(
                        pos,
                        container.getSlot(container.getSize() - 1).getFrame().getStopPos()
                )
                .build();
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
        protected Pos pos = Pos.defaultPos();
        protected Style style = Style.defaultStyle();
        protected int rowSize = 1;
        protected int amountRows = 1;

        public Builder<T> pos(Pos pos) {
            try {
                this.pos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder<T> style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
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

        public RectangularSlotContainer<T> build(SlotFactory<T> factory, int[][] indexes) {
            checkIndexes(amountRows, rowSize, indexes);
            return new RectangularSlotContainer<>(pos, factory, rowSize, amountRows, initRows(indexes, amountRows, rowSize, pos, factory));
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
        protected UnmodifiableLinearSlotContainer<T>[] initRows(int[][] indexes, int amountRows, int rowSize, Pos pos, SlotFactory<T> factory) {
            final UnmodifiableLinearSlotContainer<T>[] result = new UnmodifiableLinearSlotContainer[amountRows];
            Pos currentPos = null;
            try {
                currentPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < amountRows; i++) {
                UnmodifiableLinearSlotContainer<T> container = result[i] = UnmodifiableLinearSlotContainer.<T>builder()
                        .size(rowSize)
                        .build(currentPos, factory, indexes[i]);

                currentPos = Pos.builder()
                        .coords(
                                pos.getX(),
                                currentPos.getY()
                                        + container.getSlot(0).getFrame().getHeight()
                                        + container.getSlot(0).getStyle().getIndent().getTop()
                        )
                        .build(currentPos.getXPercentValue(), currentPos.getYPercentValue());
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
