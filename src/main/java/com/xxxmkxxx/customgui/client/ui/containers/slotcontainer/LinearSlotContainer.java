package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class LinearSlotContainer<T extends AbstractSlot> extends AbstractRowSlotContainer<T> {
    private final List<T> slots = new ArrayList<>();

    private LinearSlotContainer(int offset, Pos pos, SlotFactory<T> factory) {
        super(offset, pos, factory);
        this.currentSlotPos = pos;
    }

    @Override
    public void initRenderer(RendererType type) {
        slots.forEach(slot -> slot.initRenderer(type));
        renderer = new RendererFactory<T>().create(type);
    }

    @Override
    public void addSlot(int index) {
        T slot = factory.create(index, currentSlotPos);
        slots.add(slot);

        currentSlotPos = new Pos(slot.getFrame().getStopPos().x() + offset, pos.y());
    }

    @Override
    public void setSlot(int index, T slot) {
        slots.set(index, slot);
    }

    @Override
    public T getSlot(int index) {
        return slots.get(index);
    }

    public static class Builder<T extends AbstractSlot> {
        private List<Integer> indexes;
        private int count = 1;
        private int offset = 1;

        public Builder(List<Integer> indexes) {
            this.indexes = indexes;
        }

        public Builder(Integer ... indexes) {
            this.indexes = Arrays.asList(indexes);
        }

        public Builder<T> count(int count) {
            this.count = count;
            return this;
        }

        public Builder<T> offset(int offset) {
            this.offset = offset;
            return this;
        }

        public LinearSlotContainer<T> build(Pos pos, SlotFactory<T> factory) {
            LinearSlotContainer<T> slotContainer = new LinearSlotContainer<>(offset, pos, factory);

            for (int i = 0; i < count; i++) {
                if (i < indexes.size())
                    slotContainer.addSlot(indexes.get(i));
                else
                    slotContainer.addSlot(Integer.MIN_VALUE);
            }

            return slotContainer;
        }
    }

    public static class RendererFactory<T extends AbstractSlot> implements NodeRendererFactory<LinearSlotContainer<T>> {
        @Override
        public NodeRenderer<LinearSlotContainer<T>> create(RendererType type) {
            return this::render;
        }

        private void render(LinearSlotContainer<T> slotContainer) {
            slotContainer.getSlots().forEach(slot -> slot.getRenderer().render(slot));
        }
    }
}
