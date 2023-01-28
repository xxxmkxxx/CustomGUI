package com.xxxmkxxx.customgui;

import com.xxxmkxxx.customgui.client.hierarchy.node.NodeDrawableHelper;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import com.xxxmkxxx.customgui.client.hierarchy.stage.HudStage;
import com.xxxmkxxx.customgui.client.hierarchy.stage.ScreenStage;
import lombok.Getter;
import net.minecraft.client.util.Window;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomGUI {
    public static final NodeDrawableHelper NODE_DRAWABLE_HELPER = new NodeDrawableHelper();
    private static CustomGUI instance = null;
    private final HudStage hudStage;
    private final ScreenStage screenStage;
    private final Map<String, GUIBlock> guiBlocks;
    private final Map<String, AbstractScene> scenes;

    private CustomGUI(Window window) {
        this.hudStage = new HudStage(window.getWidth(), window.getHeight(), window.getScaledWidth(), window.getScaledHeight());
        this.screenStage = new ScreenStage(window.getWidth(), window.getHeight(), window.getScaledWidth(), window.getScaledHeight());
        this.guiBlocks = new HashMap<>();
        this.scenes = new HashMap<>();
    }

    public static CustomGUI getInstance() {
        return instance;
    }

    public void addGUIBlock(String blockId, GUIBlock block) {
        guiBlocks.put(blockId, block);
    }

    public void setActiveScene(AbstractScene scene, RendererType type) {
        switch (type) {
            case HUD: {
                hudStage.setActiveScene(scene);
                break;
            }
            case SCREEN: {
                screenStage.setActiveScene(scene);
                System.out.println("screeeeeeeeeeeeeeeeeeeeeeeeeeeeen");
                break;
            }
        }
    }

    public void setActiveScene(String blockId, RendererType type) {
        setActiveScene(guiBlocks.get(blockId).createScene(hudStage, screenStage), type);
    }

    public AbstractScene getScene(String blockId) {
        return scenes.getOrDefault(blockId, guiBlocks.get(blockId).createScene(hudStage, screenStage));
    }

    @FunctionalInterface
    public interface GUIBlock {
        AbstractScene createScene(HudStage hudStage, ScreenStage screenStage);
    }

    public static class CustomGUIInitializer {
        public static void init(Window window) {
            if (instance == null) {
                instance = new CustomGUI(window);
            }
        }
    }
}
