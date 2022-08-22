package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.common.Event;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class ItemContainer {
    @Getter
    private final UpdateEvent UPDATE_EVENT = new UpdateEvent();
    private final int INDEX;
    private final Inventory INVENTORY;
    private ItemStack lastItemStack;
    private boolean isStandardItem;

    public ItemContainer(int index, Inventory inventory) {
        this.INDEX = index;
        this.INVENTORY = inventory;
        this.lastItemStack = INVENTORY.getStack(INDEX);
        this.isStandardItem = Registry.ITEM.getId(lastItemStack.getItem()).getNamespace().equals("minecraft");
    }

    public ItemStack getItemStack() {
        return lastItemStack;
    }

    public boolean isContainedStandardItem() {
        return isStandardItem;
    }

    public void update() {
        ItemStack currentItemStack = INVENTORY.getStack(INDEX);
        Item item = currentItemStack.getItem();

        if (!(lastItemStack.getItem() == item)) {
            lastItemStack = currentItemStack;
            isStandardItem = Registry.ITEM.getId(item).getNamespace().equals("minecraft");
            UPDATE_EVENT.callAll();
        }

        currentItemStack = null; //help GC
        item = null; //help GC
    }

    public static class UpdateEvent implements Event<ItemContainerHandler> {
        private final List<ItemContainerHandler> handlers = new LinkedList<>();

        @Override
        public void callAll() {
            handlers.forEach(ItemContainerHandler::onContainerUpdate);
        }

        @Override
        public void addHandler(ItemContainerHandler handler) {
            handlers.add(handler);
        }

        @Override
        public void removeHandler(ItemContainerHandler handler) {
            handlers.remove(handler);
        }
    }
}
