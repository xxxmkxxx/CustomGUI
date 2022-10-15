package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.animations.standard.button.TargetingButtonAnimation;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.ClickEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.target.TargetEvent;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.slotcontainer.SquareSlotContainer;
import com.xxxmkxxx.customgui.client.ui.controls.button.SimpleButton;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import com.xxxmkxxx.timecontrol.client.TimeControlClient;
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
    private static final StaticFrame FRAME = new StaticFrame(POS, POS2, false);
    private static final int COLOR = 0xAF3C3B36;
    private static final Inventory INVENTORY = new TestInventory();
    private static final SimpleSlot.Factory SIMPLE_SLOT_FACTORY = new SimpleSlot.Factory(18, 18, COLOR, new TestInventory());
    private static final SquareSlotContainer.Builder<SimpleSlot> BUILDER = new SquareSlotContainer.Builder<>(
            POS,
            SIMPLE_SLOT_FACTORY,
            new int[][]{
                    {0, 100, 100},
                    {1, 100, 100},
                    {2, 100, 100}
            }
    ).size(3);

    private static final SquareSlotContainer<SimpleSlot> SLOT_CONTAINER = BUILDER.build();

    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.SCREEN);
        SimpleButton button = SimpleButton.builder().startPos(POS).name("test button").build();

        button.addEvent((ClickEvent) () -> {
            System.out.println("hoho");
        });

        button.addEvent((TargetEvent) () -> {
            scene.getAnimationManager().addAnimation(new TargetingButtonAnimation(button));
        });

        scene.addElement(button);
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
