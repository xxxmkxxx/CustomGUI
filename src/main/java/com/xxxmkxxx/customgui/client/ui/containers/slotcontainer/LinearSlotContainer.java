package com.xxxmkxxx.customgui.client.ui.containers.slotcontainer;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.slot.AbstractSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SlotFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@SuppressWarnings("unused")
public class LinearSlotContainer<T extends AbstractSlot> extends AbstractRowSlotContainer<T> {
    private final List<T> slots = new ArrayList<>();

    protected LinearSlotContainer(Pos startPos, SlotFactory<T> factory) {
        super(factory);
        this.frame = new SimpleFrame(startPos, 0, 0);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new RendererFactory<T>().create(type);
        slots.forEach(slot -> slot.initRenderer(type));
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);
        slots.forEach(slot -> slot.initSection(initMethod));
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        slots.forEach(slot -> slot.init(initMethod));
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        slots.forEach(slot -> slot.scaling(window));
    }

    @Override
    public void hide() {
        super.hide();
        slots.forEach(AbstractNode::hide);
    }

    @Override
    public void display() {
        super.display();
        slots.forEach(AbstractNode::display);
    }

    @Override
    public void addSlot(int index) {
        T slot = factory.create(
                index,
                Pos.builder()
                        .coords(frame.getStopPos().getX(), frame.getStartPos().getY())
                        .build(frame.getLastXPercentValue(), frame.getLastYPercentValue())
        );
        slots.add(slot);

        frame.moveStopPos(slot.getFrame().getWidth(), 0);
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

    public static <S extends AbstractSlot> Builder<S> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends AbstractSlot> {
        private Pos pos = Pos.defaultPos();
        private Style style = Style.defaultStyle();

        public Builder<T> style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder<T> pos(Pos pos) {
            try {
                this.pos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public LinearSlotContainer<T> build(SlotFactory<T> factory, List<Integer> indexes) {
            LinearSlotContainer<T> slotContainer = new LinearSlotContainer<>(pos, factory);

            for (int i = 0; i < indexes.size(); i++) {
                slotContainer.addSlot(indexes.get(i));
            }

            return slotContainer;
        }

        public LinearSlotContainer<T> build(SlotFactory<T> factory, Integer ... indexes) {
            return build(factory, new ArrayList<>(List.of(indexes)));
        }
    }

    public static class RendererFactory<T extends AbstractSlot> implements NodeRendererFactory<LinearSlotContainer<T>> {
        @Override
        public NodeRenderer<LinearSlotContainer<T>> create(RendererType type) {
            return this::render;
        }

        @SuppressWarnings("unchecked")
        private void render(LinearSlotContainer<T> slotContainer) {
            slotContainer.getSlots().forEach(slot -> slot.getState().execute(slot, slot.getRenderer()));
        }
    }
}
