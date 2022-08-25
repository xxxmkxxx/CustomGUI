package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.LinearSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.RectangularSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.SquareSlotContainer;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.UnmodifiableLinearSlotContainer;
import com.xxxmkxxx.customgui.client.ui.controls.field.TextField;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TestHud {
    private static final Pos POS = new Pos(175, 100);
    private static final int COLOR = 0xAF3C3B36;
    private static final Inventory INVENTORY = new TestInventory();
    private static final SimpleSlot.Factory SIMPLE_SLOT_FACTORY = new SimpleSlot.Factory(18, 18, COLOR, new TestInventory());

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

    private static final SquareSlotContainer.Builder<SimpleSlot> SIMPLE_SLOT_CONTAINER_BUILDER = new SquareSlotContainer.Builder<SimpleSlot>(
            new int[][]{
                    {0, 1, 2, 100, 100, 100},
                    {0, 1, 2, 100, 100, 1},
                    {0, 1, 100, 100, 100, 100},
                    {100, 1, 2, 100, 100, 100},
                    {0, 1, 2, 100, 100, 100},
                    {0, 1, 2, 1, 100, 100}
            }
    ).size(6);

    private static final RectangularSlotContainer.Builder<SimpleSlot> SIMPLE_SLOT_CONTAINER_BUILDER_1 = new RectangularSlotContainer.Builder<SimpleSlot>(
            new int[][]{
                    {0, 100, 100, 100, 100, 100},
                    {1, 100, 100, 100, 100, 100},
                    {2, 100, 100, 100, 100, 100},
                    {3, 100, 100, 100, 100, 100}
            }
    ).amountRows(4).rowSize(6);

    private static final SquareSlotContainer<SimpleSlot> SQUARE_CLOT_CONTAINER =  SIMPLE_SLOT_CONTAINER_BUILDER.build(POS, SIMPLE_SLOT_FACTORY);
    private static final RectangularSlotContainer<SimpleSlot> SQUARE_CLOT_CONTAINER_1 =  SIMPLE_SLOT_CONTAINER_BUILDER_1.build(POS, SIMPLE_SLOT_FACTORY);

    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.SCREEN);
        scene.addElement(SQUARE_CLOT_CONTAINER_1);

        CustomGUIClient.SCREEN_STAGE.setScene(scene);
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
