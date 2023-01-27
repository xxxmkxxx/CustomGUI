package com.xxxmkxxx.customgui.client.hierarchy.stage;

import com.xxxmkxxx.customgui.client.hierarchy.scene.AbstractScene;

public interface Stage {
    void render();
    void setActiveScene(AbstractScene scene);
    void clearScene();
    void init();
}
