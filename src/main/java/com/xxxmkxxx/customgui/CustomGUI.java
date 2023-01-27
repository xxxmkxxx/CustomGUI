package com.xxxmkxxx.customgui;

import com.xxxmkxxx.customgui.client.hierarchy.node.NodeDrawableHelper;
import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;
import com.xxxmkxxx.customgui.client.hierarchy.stage.AbstractStage;
import com.xxxmkxxx.customgui.client.hierarchy.stage.HudStage;
import com.xxxmkxxx.customgui.client.hierarchy.stage.ScreenStage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomGUI {
    public static final NodeDrawableHelper NODE_DRAWABLE_HELPER = new NodeDrawableHelper();
    private final HudStage hudStage;
    private final ScreenStage screenStage;
    private final Map<String, GUIBlock> guiBlocks;
    private final Map<String, AbstractScene> scenes;
    public CustomGUI() {
        this.hudStage = new HudStage();
        this.screenStage = new ScreenStage();
        this.guiBlocks = new HashMap<>();
        this.scenes = new HashMap<>();
    }

    public void init() {
        guiBlocks.forEach((sceneId, guiBlock) -> {
            AbstractScene scene = guiBlock.createBlock(hudStage, screenStage);
            scenes.put(sceneId, scene);
        });
    }

    public void addGUIBlock(String blockId, GUIBlock block) {
        guiBlocks.put(blockId, block);
    }

    public void setActiveScene(AbstractScene scene, AbstractStage stage) {
        stage.setActiveScene(scene);
    }
    public void setActiveScene(String blockId, AbstractStage stage) {
        setActiveScene(scenes.getOrDefault(blockId, guiBlocks.get(blockId).createBlock(hudStage, screenStage)), stage);
    }

    @FunctionalInterface
    public interface GUIBlock {
        AbstractScene createBlock(HudStage hudStage, ScreenStage screenStage);
    }
}
