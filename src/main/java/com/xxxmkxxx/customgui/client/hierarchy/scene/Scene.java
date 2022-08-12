package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public interface Scene {
    void addElement(AbstractNode node);
    void render();
}
