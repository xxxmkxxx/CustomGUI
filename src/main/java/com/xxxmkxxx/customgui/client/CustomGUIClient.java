package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.common.inventory.AbstractInventory;
import com.xxxmkxxx.customgui.client.common.inventory.InventoryType;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Color;
import com.xxxmkxxx.customgui.client.hierarchy.style.Opacity;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.button.ImagedButton;
import com.xxxmkxxx.customgui.client.ui.controls.button.SimpleButton;
import com.xxxmkxxx.customgui.client.ui.controls.image.SimpleImage;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class CustomGUIClient implements ClientModInitializer {
    private static AbstractInventory inventory = new AbstractInventory(InventoryType.ENTITY) {
        private final List<ItemStack> items = Arrays.asList(
                ItemStack.EMPTY, new ItemStack(Items.BARREL,5), new ItemStack(Items.ACACIA_PLANKS, 10),
                new ItemStack(Items.BLUE_GLAZED_TERRACOTTA, 8), new ItemStack(Items.END_STONE_BRICKS, 1)
        );

        @Override
        public int size() {
            return items.size();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return items.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack removeStack(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {

        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {

        }
    };

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            CustomGUI customGUI = CustomGUI.getInstance();

            customGUI.addGUIBlock("test", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                Style style = new Style();
                style.setColor(new Color("2ad43b"));
                style.setBackground(Background.builder().opacity(new Opacity(100)).type(Background.Type.COLORED).color(new Color("142615")).build());
                style.setOpacity(new Opacity(80));

                SimpleImage image = SimpleImage.builder()
                        .startPos(new Pos(150, 150))
                        .width(20).height(20)
                        .identifier(new Identifier("customgui", "textures/gui/img_1.png"))
                        .build();
                image.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("Iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiimage"), false);
                });

                SimpleButton button = SimpleButton.builder()
                        .startPos(new Pos(150, 150))
                        .text("test")
                        .build();
                button.setLeftClickAction(() -> {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("teeeest"), false);
                });
                button.setStyle(style);

                ImagedButton imagedButton = ImagedButton.builder()
                        .image(image)
                        .text("aboba")
                        .pos(new Pos(150, 150))
                        .style(style)
                        .build();

                SimpleText simpleText = SimpleText.builder()
                        .pos(image.getFrame().getStopPos())
                        .style(style)
                        .build();

                /*EventBus.CHANGE_FRAME_EVENT.addHandler(button.getFrame(), (ChangeFrameEventHandler) () -> {
                    ((DynamicFrame)image.getFrame()).setStopPos(button.getFrame().getStopPos());
                });*/

                //scene.addElement(image);
                //scene.addElement(button);
                //scene.addElement(simpleText);
                scene.addElement(imagedButton);

                return scene;
            });
            customGUI.addGUIBlock("test2", (hudStage, screenStage) -> {
                SimpleScene scene = new SimpleScene(RendererType.SCREEN);

                SimpleSlot.Factory slotFactory = SimpleSlot.factoryBuilder(18, 18,inventory).build();

                scene.addElement(slotFactory.create(2, new Pos(50, 50)));

                return scene;
            });

            customGUI.setActiveScene("test2", RendererType.SCREEN);
        });
    }
}
