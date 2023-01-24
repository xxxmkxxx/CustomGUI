package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.geometry.frame.DynamicFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

@Getter
@SuppressWarnings("unused")
public class SimpleSlot extends AbstractSlot implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private SimpleText amountItemsText;
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    protected SimpleSlot(int index, AbstractInventory inventory, StaticFrame frame) {
        super(index, inventory, frame);
        this.leftClickAction = () -> {};
        this.hoverAction = () -> {};
        this.resetHoverAction = () -> {};
        this.amountItemsText = SimpleText.builder().build();
        amountItemsText.setStyle(this.getStyle());
        updateAmountItemsText(inventory.getStack(index), frame);
    }

    public void updateAmountItemsText(ItemStack itemStack, StaticFrame frame) {
        Text amount = Text.of(Integer.toString(itemStack.getCount()));

        Pos startPos = new Pos(
                frame.getStopPos().x() - Utils.getTextWidth(amount),
                frame.getStopPos().y() - Utils.getTextHeight()
        );

        Pos stopPos = new Pos(
                startPos.x() + Utils.getTextWidth(amount),
                frame.getStopPos().y() + Utils.getTextHeight()
        );

        ((DynamicFrame) amountItemsText.getFrame()).setStartPos(startPos);
        ((DynamicFrame) amountItemsText.getFrame()).setStopPos(stopPos);
        amountItemsText.setText(amount);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        amountItemsText.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
    }

    @Override
    public void onLeftClick() {
        leftClickAction.run();
    }

    @Override
    public void onHover() {
        hoverAction.run();
    }

    @Override
    public void onResetHover() {
        resetHoverAction.run();
    }

    public void setLeftClickAction(Runnable leftClickAction) {
        this.leftClickAction = leftClickAction;
        EventBus.LEFT_CLICK_EVENT.addHandler(this, this);
    }

    public void setHoverAction(Runnable hoverAction) {
        this.hoverAction = hoverAction;
        EventBus.HOVER_EVENT.addHandler(this, this);
    }

    public void setResetHoverAction(Runnable resetHoverAction) {
        this.resetHoverAction = resetHoverAction;
        EventBus.RESET_HOVER_EVENT.addHandler(this, this);
    }

    public Identifier getItemAtlas(Item item) {
        Identifier itemIdentifier = Registry.ITEM.getId(item);

        return new Identifier(itemIdentifier.getNamespace(), "textures/gui/" + itemIdentifier.getPath());
    }

    @Setter
    @RequiredArgsConstructor
    public static class Factory implements SlotFactory<SimpleSlot> {
        private final int width;
        private final int height;
        private final int backgroundColor;
        private final AbstractInventory inventory;
        private Style style;
        private Consumer<ItemStack> leftClickAction = (itemStack) -> {};
        private Runnable hoverAction = () -> {};
        private Runnable resetHoverAction = () -> {};

        @Override
        public SimpleSlot create(int index, Pos pos) {
            SimpleSlot slot = new SimpleSlot(index, inventory, new StaticFrame(pos, width, height, false));
            slot.setLeftClickAction(() -> leftClickAction.accept(inventory.getStack(index)));
            slot.setHoverAction(hoverAction);
            slot.setResetHoverAction(resetHoverAction);
            slot.setStyle(style);

            return slot;
        }
    }

    public static class FactoryBuilder {
        private Factory factory;

        public FactoryBuilder(int width, int height, int backgroundColor, AbstractInventory inventory) {
            this.factory = new Factory(width, height, backgroundColor, inventory);
        }

        public FactoryBuilder leftClickAction(Consumer<ItemStack> leftClickAction) {
            factory.setLeftClickAction(leftClickAction);
            return this;
        }

        public FactoryBuilder hoverAction(Runnable hoverAction) {
            factory.setHoverAction(hoverAction);
            return this;
        }

        public FactoryBuilder resetHoverAction(Runnable resetHoverAction) {
            factory.setResetHoverAction(resetHoverAction);
            return this;
        }

        public FactoryBuilder style(Style style) {
            factory.setStyle(style);
            return this;
        }

        public Factory build() {
            return factory;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleSlot> {
        private ParametrizedSelfDestructionMethod<SimpleSlot> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<SimpleSlot> backgroundRenderMethod = simpleSlot -> {};
        private Consumer<SimpleSlot> standardImageRenderMethod = simpleSlot -> {};
        private Consumer<SimpleSlot> customImageRenderMethod = simpleSlot -> {};

        public RendererFactory() {
            initBackgroundMethod.setAction(simpleSlot -> {
                    backgroundRenderMethod = Background.chooseBackground(simpleSlot.getStyle().getBackground().getType());
                });
            standardImageRenderMethod = simpleSlot -> {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        simpleSlot.getItemStack(),
                        simpleSlot.getFrame()
                );
            };
            customImageRenderMethod = simpleSlot -> {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        simpleSlot.getMatrixStack(),
                        simpleSlot.getFrame(),
                        simpleSlot.getItemAtlas(simpleSlot.getItemStack().getItem())
                );
            };
        }

        @Override
        public NodeRenderer<SimpleSlot> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleSlot slot) {
            initBackgroundMethod.execute(slot);

            backgroundRenderMethod.accept(slot);

            ItemStack itemStack = slot.getItemStack();
            if (itemStack == ItemStack.EMPTY) return;
            if (slot.isStandardItem()) standardImageRenderMethod.accept(slot);
            else customImageRenderMethod.accept(slot);

            slot.getAmountItemsText().getRenderer().render(slot.getAmountItemsText());
        }
    }
}
