package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.geometry.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.States;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
import com.xxxmkxxx.customgui.client.ui.controls.field.TextField;
import com.xxxmkxxx.customgui.client.ui.controls.slot.SimpleSlot;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class TestHud {
    /*private static final Identifier TEXTURE = new Identifier("customgui", "textures/gui/img_1.png");
    private static final int height = 50;
    private static final int width = height;
    private static final NativeImage nativeImage = new NativeImage(width, height, false);
    private static final SpriteAtlasTexture spriteAtlasTexture = new SpriteAtlasTexture(TEXTURE);
    private static final AnimationResourceMetadata metadata = new AnimationResourceMetadata(new ArrayList<>(), 100, 100, 1, false);
    private static final Sprite.Info info = new Sprite.Info(TEXTURE, width, height, metadata);
    private static final FabricSprite sprite = new FabricSprite(spriteAtlasTexture, info, 1, 1, 1, 0, 0, nativeImage);*/

    private static final SimplePane PANE = SimplePane.builder()
            .matrixStack(new MatrixStack())
            .frame(new Frame(100, 100, 50, 50, false))
            .state(States.DISPLAYED)
            .color(0xFF786060)
            .build();

    private static final TextField FIELD = TextField.builder()
            .textColor(0xFF030202)
            .pos(100, 100)
            .text("test text")
            .build();

    private static final SimpleSlot SLOT = new SimpleSlot(
            1,
            1, new TestInventory(),
            new Pos(50, 50),
            new Frame(50, 50, 20, 20, false),
            0xAF3C3B36
    );


    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.SCREEN);
        scene.addElement(FIELD);
        scene.addElement(SLOT);

        CustomGUIClient.SCREEN_STAGE.setScene(scene);
    }

    private static class TestInventory implements Inventory {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return null;
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
