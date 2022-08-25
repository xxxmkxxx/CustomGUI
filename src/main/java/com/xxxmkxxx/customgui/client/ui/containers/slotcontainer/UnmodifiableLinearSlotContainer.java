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
public class UnmodifiableLinearSlotContainer<T extends AbstractSlot> extends AbstractRowSlotContainer<T> {
    private final int size;
    private final Object[] slots;

    private UnmodifiableLinearSlotContainer(int offset, Pos pos, SlotFactory<T> factory, int[] indexes) {
        super(offset, pos, factory);
        this.size = indexes.length;
        this.slots = initSlots(indexes, offset, pos, factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initRenderer(RendererType type) {
        for (int i = 0; i < size; i++) {
            T slot = (T) slots[i];
            slot.initRenderer(type);
        }

        renderer = new RendererFactory<T>().create(type);
    }

    @Override
    public void addSlot(int index) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void setSlot(int index, T slot) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getSlot(int index) {
        Validator.checkArrayIndex(index, slots);

        return (T) slots[index];
    }

    @SuppressWarnings("unchecked")
    private Object[] initSlots(int[] indexes, int offset, Pos pos, SlotFactory<T> factory) {
        Object[] result = new Object[size];
        Pos currentPos = pos;

        for (int i = 0; i < size; i++) {
            T slot = factory.create(indexes[i], currentPos);

            result[i] = slot;

            currentPos = new Pos(slot.getFrame().getStopPos().x() + offset, pos.y());
        }

        return result;
    }

    public static class Builder<T extends AbstractSlot> {
        private final int[] indexes;
        private int size = 1;
        private int offset = 1;

        public Builder(int ... indexes) {
            this.indexes = indexes;
        }

        public Builder<T> size(int size) {
            Validator.checkArraySize(size);
            this.size = size;
            return this;
        }

        public Builder<T> offset(int offset) {
            this.offset = offset;
            return this;
        }

        public UnmodifiableLinearSlotContainer<T> build(Pos pos, SlotFactory<T> factory) {
            return new UnmodifiableLinearSlotContainer<>(offset, pos, factory, indexes);
        }
    }

    public static class RendererFactory<T extends AbstractSlot> implements NodeRendererFactory<UnmodifiableLinearSlotContainer<T>> {
        @Override
        public NodeRenderer<UnmodifiableLinearSlotContainer<T>> create(RendererType type) {
            return this::render;
        }

        @SuppressWarnings("unchecked")
        private void render(UnmodifiableLinearSlotContainer<T> container) {
            Arrays.stream(container.getSlots()).forEach(slot -> ((T)slot).getRenderer().render((T)slot));
        }
    }
}