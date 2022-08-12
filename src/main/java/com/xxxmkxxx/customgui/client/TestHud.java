package com.xxxmkxxx.customgui.client;

import com.xxxmkxxx.customgui.client.common.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.node.State;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.Scene;
import com.xxxmkxxx.customgui.client.hierarchy.scene.SimpleScene;
import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;
import net.fabricmc.fabric.impl.client.texture.FabricSprite;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

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
            .state(State.DISPLAYED)
            .color(0xFF786060)
            .build();

    public static void render() {
        SimpleScene scene = new SimpleScene(RendererType.HUD);
        scene.addElement(PANE);

        CustomGUIClient.HUD_STAGE.setScene(scene);
    }
}
