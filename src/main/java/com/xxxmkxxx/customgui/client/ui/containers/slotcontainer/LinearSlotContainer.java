package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.common.SimpleBuilder;
import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.TargetManager;
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
@SuppressWarnings("unused")
public class LinearSlotContainer<T extends AbstractSlot> extends AbstractRowSlotContainer<T> {
    private final List<T> slots = new ArrayList<>();

    protected LinearSlotContainer(int offset, Pos startPos, SlotFactory<T> factory) {
        super(offset, factory);
        this.frame = new DynamicFrame(startPos, 0, 0, false);
        this.currentSlotPos = startPos;
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

        currentSlotPos = new Pos(slot.getFrame().getStopPos().x() + offset, frame.getStartPos().y());
        ((DynamicFrame)frame).setStopPos(currentSlotPos);
    }

    @Override
    public void setSlot(int index, T slot) {
        Validator.checkArrayIndex(index, slots.toArray());
        slots.set(index, slot);
    }

    @Override
    public T getSlot(int index) {
        Validator.checkArrayIndex(index, slots.toArray());
        return slots.get(index);
    }

    @Override
    public void updateTarget(int xPos, int yPos, TargetManager targetManager) {
        super.updateTarget(xPos, yPos, targetManager);

        if (isTarget) {
            for (AbstractNode slot : slots) {
                slot.updateTarget(xPos, yPos, targetManager);
            }
        }
    }


    public static Builder<? extends AbstractSlot> builder(Pos pos, SlotFactory<? extends AbstractSlot> factory, List<Integer> indexes) {
        return new Builder<>(pos, factory, indexes);
    }

    public static Builder<? extends AbstractSlot> builder(Pos pos, SlotFactory<? extends AbstractSlot> factory, Integer ... indexes) {
        return new Builder<>(pos, factory, indexes);
    }


    public static class Builder<T extends AbstractSlot> implements SimpleBuilder<LinearSlotContainer<T>> {
        private final List<Integer> indexes;
        private final Pos pos;
        private final SlotFactory<T> factory;
        private int count = 1;
        private int offset = 1;

        public Builder(Pos pos, SlotFactory<T> factory, List<Integer> indexes) {
            this.pos = pos;
            this.factory = factory;
            this.indexes = indexes;
        }

        public Builder(Pos pos, SlotFactory<T> factory, Integer ... indexes) {
            this(pos, factory, Arrays.asList(indexes));
        }

        public Builder<T> count(int count) {
            this.count = count;
            return this;
        }

        public Builder<T> offset(int offset) {
            this.offset = offset;
            return this;
        }

        public LinearSlotContainer<T> build() {
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

        @SuppressWarnings("unchecked")
        private void render(LinearSlotContainer<T> slotContainer) {
            slotContainer.getSlots().forEach(slot -> slot.getRenderer().render(slot));
        }
    }
}
