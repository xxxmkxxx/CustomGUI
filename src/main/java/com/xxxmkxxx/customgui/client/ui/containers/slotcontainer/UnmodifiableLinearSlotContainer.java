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

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@SuppressWarnings("unused")
public class UnmodifiableLinearSlotContainer<T extends AbstractSlot> extends AbstractRowSlotContainer<T> {
    private final int size;
    private final Object[] slots;

    @SuppressWarnings("unchecked")
    protected UnmodifiableLinearSlotContainer(Pos pos, SlotFactory<T> factory, int[] indexes) {
        super(factory);
        this.size = indexes.length;
        this.frame = SimpleFrame.builder().startPos(pos).heightPercent(0.0).widthPercent(0.0).build();
        updateIndents();
        this.slots = initSlots(indexes, factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        renderer = new RendererFactory<T>().create(type);

        for (int i = 0; i < size; i++) {
            T slot = (T) slots[i];
            slot.initRenderer(type);
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < size; i++) {
            ((AbstractSlot)slots[i]).update();
        }
        updateIndents();
    }

    private void updateIndents() {
        int leftSlotContainerMargin = style.getMargins().getLeft();
        int topSlotContainerMargin = style.getMargins().getTop();

        frame.moveStartPos(leftSlotContainerMargin, topSlotContainerMargin);
        frame.moveStopPos(leftSlotContainerMargin, topSlotContainerMargin);
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);

        for (int i = 0; i < size; i++) {
            ((AbstractSlot)slots[i]).initSection(initMethod);
        }
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        for (int i = 0; i < size; i++) {
            ((AbstractSlot)slots[i]).init(initMethod);
        }
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        for (int i = 0; i < size; i++) {
            ((AbstractSlot)slots[i]).scaling(window);
        }
    }

    @Override
    public void hide() {
        super.hide();
        for (int i = 0; i < size; i++) {
            ((AbstractSlot)slots[i]).hide();
        }
    }

    @Override
    public void display() {
        super.display();
        for (int i = 0; i < size; i++) {
            ((AbstractSlot)slots[i]).display();
        }
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

    public static <S extends AbstractSlot> Builder<S> builder() {
        return new Builder<>();
    }

    private Object[] initSlots(int[] indexes, SlotFactory<T> factory) {
        Object[] result = new Object[size];

        for (int i = 0; i < size; i++) {
            T slot = factory.create(
                    indexes[i],
                    Pos.builder()
                            .coords(frame.getStopPos().getX(), frame.getStopPos().getY())
                            .build(frame.getLastXPercentValue(), frame.getLastYPercentValue())
            );

            //gag
            System.out.println("slot width - " + slot.getFrame().getWidth() + "\n");
            System.out.println("slot height - " + slot.getFrame().getHeight() + "\n");

            result[i] = slot;

            frame.moveStopPos(slot.getFrame().getWidth() + slot.getStyle().getMargins().getRight(), 0);
        }

        return result;
    }

    public static class Builder<T extends AbstractSlot> {
        private Pos pos = Pos.defaultPos();
        private Style style = Style.defaultStyle();
        private int size = 1;

        public Builder<T> style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder<T> size(int size) {
            Validator.checkArraySize(size);
            this.size = size;
            return this;
        }

        public UnmodifiableLinearSlotContainer<T> build(Pos pos, SlotFactory<T> factory, int ... indexes) {
            return new UnmodifiableLinearSlotContainer<>(pos, factory, indexes);
        }
    }

    public static class RendererFactory<T extends AbstractSlot> implements NodeRendererFactory<UnmodifiableLinearSlotContainer<T>> {
        @Override
        public NodeRenderer<UnmodifiableLinearSlotContainer<T>> create(RendererType type) {
            return this::render;
        }

        @SuppressWarnings("unchecked")
        private void render(UnmodifiableLinearSlotContainer<T> container) {
            Arrays.stream(container.getSlots()).forEach(slot -> ((T)slot).getState().execute((T)slot, ((T)slot).getRenderer()));
        }
    }
}
