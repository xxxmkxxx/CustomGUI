package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.CustomGUIClient;
import com.xxxmkxxx.customgui.client.common.inventory.InventoryType;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.networking.packages.Packages;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

@Getter
@SuppressWarnings("unused")
public class SimpleSlot extends AbstractSlot implements LeftClickEventHandler, HoverEventHandler, ResetHoverEventHandler {
    private final int backgroundColor;
    protected Runnable leftClickAction;
    protected Runnable hoverAction;
    protected Runnable resetHoverAction;

    protected SimpleSlot(int index, AbstractInventory inventory, StaticFrame frame, int backgroundColor) {
        super(index, inventory, frame);
        this.backgroundColor = backgroundColor;
        this.leftClickAction = () -> {};
        this.hoverAction = () -> {};
        this.resetHoverAction = () -> {};
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
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
        private Consumer<ItemStack> leftClickAction = (itemStack) -> {};
        private Runnable hoverAction = () -> {};
        private Runnable resetHoverAction = () -> {};

        @Override
        public SimpleSlot create(int index, Pos pos) {
            SimpleSlot slot = new SimpleSlot(index, inventory, new StaticFrame(pos, width, height, false), backgroundColor);
            slot.setLeftClickAction(() -> leftClickAction.accept(inventory.getStack(index)));
            slot.setHoverAction(hoverAction);
            slot.setResetHoverAction(resetHoverAction);

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

        public Factory build() {
            return factory;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimpleSlot> {
        @Override
        public NodeRenderer<SimpleSlot> create(RendererType type) {
            return this::render;
        }

        private void render(SimpleSlot slot) {
            ItemStack itemStack = slot.getItemStack();

            CustomGUIClient.NODE_DRAWABLE_HELPER.fillFrame(
                    slot.getMatrixStack(),
                    slot.getFrame(),
                    slot.getBackgroundColor()
            );

            if (itemStack == ItemStack.EMPTY)
                return;

            if (slot.isStandardItem()) {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        itemStack,
                        slot.getFrame()
                );
            } else {
                CustomGUIClient.NODE_DRAWABLE_HELPER.drawTexture(
                        slot.getMatrixStack(),
                        slot.getFrame(),
                        slot.getItemAtlas(itemStack.getItem())
                );
            }

        }
    }
}
