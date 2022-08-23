package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.ItemContainer;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class LinearSlotContainer<T extends AbstractSlot> extends AbstractRowSlotContainer<T> {
    private final List<T> slotList = new ArrayList<>();

    private LinearSlotContainer(int offset) {
        super(offset);
    }

    @Override
    public void initRenderer(RendererType type) {
        slotList.forEach(slot -> slot.initRenderer(type));
        renderer = new RendererFactory<T>().create(type);
    }

    @Override
    public void addSlot(T slot) {
        slotList.add(slot);
    }

    @Override
    public T getSlot(int index) {
        return slotList.get(index);
    }

    public static class Builder<T extends AbstractSlot> {
        private List<ItemContainer> itemContainers;

        public Builder(List<ItemContainer> itemContainers) {
            this.itemContainers = itemContainers;
        }

        public Builder(ItemContainer ... itemContainers) {
            this.itemContainers = Arrays.asList(itemContainers);
        }

        private void initSlotContainer(LinearSlotContainer<T> slotContainer, int count, Frame frame, int offset, SlotFactory<T> factory) {
            Frame currentFrame = frame;

            for (int i = 0; i < count; i++) {
                slotContainer.addSlot(factory.create(currentFrame, itemContainers.get(i)));
                currentFrame = new Frame(
                        currentFrame.getStopPos().x() + offset,
                        currentFrame.getStartPos().y(),
                        currentFrame.getWidth(),
                        currentFrame.getHeight(),
                        false
                );
            }
        }

        public LinearSlotContainer<T> build(int count, Frame frame, int offset, SlotFactory<T> factory) {
            LinearSlotContainer<T> slotContainer = new LinearSlotContainer<>(offset);

            initSlotContainer(slotContainer, count, frame, offset, factory);

            return slotContainer;
        }
    }

    public static class RendererFactory<T extends AbstractSlot> implements NodeRendererFactory<LinearSlotContainer<T>> {
        @Override
        public NodeRenderer<LinearSlotContainer<T>> create(RendererType type) {
            return this::render;
        }

        private void render(LinearSlotContainer<T> slotContainer) {
            slotContainer.getSlotList().forEach(slot -> slot.getRenderer().render(slot));
        }
    }
}
