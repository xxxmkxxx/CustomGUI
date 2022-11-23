package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.RectangularSlotContainer;
import com.xxxmkxxx.customgui.client.ui.controls.field.TextField;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TestHud {
    private static final Pos POS = new Pos(10, 10);
    private static final Pos POS2 = new Pos(200, 200);
    private static final int COLOR = 0xAF3C3B36;
    private static final SimpleSlot.Factory SIMPLE_SLOT_FACTORY = new SimpleSlot.Factory(18, 18, COLOR, new TestInventory());
    private static final RectangularSlotContainer.Builder<SimpleSlot> BUILDER = new RectangularSlotContainer.Builder<>(
        POS, SIMPLE_SLOT_FACTORY,
            new int[][]{
                    {0, 1, 2, 3, 4, 5, 6},
                    {7, 8, 9, 10, 11, 12, 13},
                    {14, 15, 16, 17, 18, 19, 20},
                    {21, 22, 23, 24, 25, 26, 27},
            }
    );
    private static final RectangularSlotContainer<SimpleSlot> SLOT_CONTAINER = BUILDER.amountRows(4).rowSize(7).build();

    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.SCREEN);

        TextField textField = TextField.builder().text("Max loh").textColor(0xFF1ac9c7).pos(POS2).build();

        scene.addElement(textField);

        CustomGUIClient.SCREEN_STAGE.setActiveScene(scene);
    }

    private static class TestInventory implements Inventory {
        private List<ItemStack> itemStacks = Arrays.asList(
                new ItemStack(Items.BASALT, 1),
                new ItemStack(Items.ANDESITE_WALL, 1),
                new ItemStack(Items.BIRCH_LOG, 1),
                ItemStack.EMPTY
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
            if (slot >= size())
                return ItemStack.EMPTY;
            else
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
