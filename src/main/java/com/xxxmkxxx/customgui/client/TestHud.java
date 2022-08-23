package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.LinearSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.RowSlotContainer;
import com.xxxmkxxx.customgui.client.ui.controls.field.TextField;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import com.xxxmkxxx.customgui.client.ui.controls.slot.ItemContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TestHud {
    private static final Pos POS = new Pos(50, 50);
    private static final int COLOR = 0xAF3C3B36;
    private static final Inventory INVENTORY = new TestInventory();

    private static final SimplePane PANE = SimplePane.builder()
            .matrixStack(new MatrixStack())
            .frame(new Frame(100, 100, 50, 50, false))
            .state(States.DISPLAYED)
            .color(0xFF786060)
            .build();

    private static final TextField FIELD = TextField.builder()
            .textColor(0xFF030202)
            .pos(POS)
            .text("test text")
            .build();

    private static final LinearSlotContainer<SimpleSlot> SLOT_CONTAINER = new LinearSlotContainer.Builder<SimpleSlot>(
            new ItemContainer(0, INVENTORY),
            new ItemContainer(1, INVENTORY),
            new ItemContainer(2, INVENTORY)
    ).build(
            3,
            new Frame(POS, 20, 20, true),
            1,
            new SimpleSlot.Factory(1, COLOR)
    );


    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.SCREEN);
        scene.addElement(SLOT_CONTAINER);

        CustomGUIClient.SCREEN_STAGE.setScene(scene);
    }

    private static class TestInventory implements Inventory {
        private List<ItemStack> itemStacks = Arrays.asList(
                new ItemStack(Items.BASALT, 1),
                new ItemStack(Items.ANDESITE_WALL, 1),
                new ItemStack(Items.BIRCH_LOG, 1)
        );

        @Override
        public int size() {
            return itemStacks.size();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return itemStacks.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return null;
        }

        @Override
        public ItemStack removeStack(int slot) {
            return null;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {

        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return false;
        }

        @Override
        public void clear() {

        }
    }
}
