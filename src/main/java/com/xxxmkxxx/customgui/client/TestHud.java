package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
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

@Environment(EnvType.CLIENT)
public class TestHud {
    private static final Pos POS = new Pos(50, 50);

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

    private static final SimpleSlot SLOT = new SimpleSlot(
            1,
            new ItemContainer(1, new TestInventory()),
            POS,
            new Frame(POS, 30, 30, true),
            0xAF3C3B36
    );


    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.SCREEN);
        //scene.addElement(FIELD);
        scene.addElement(SLOT);

        CustomGUIClient.SCREEN_STAGE.setScene(scene);
    }

    private static class TestInventory implements Inventory {
        private ItemStack itemStack = new ItemStack(Items.ACACIA_DOOR, 1);

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return itemStack;
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
