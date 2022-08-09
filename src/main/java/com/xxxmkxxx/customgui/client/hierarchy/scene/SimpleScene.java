package com.xxxmkxxx.customgui.client.hierarchy.scene;

import com.xxxmkxxx.customgui.client.hierarchy.Node;

import java.util.Arrays;
import java.util.LinkedList;

public class SimpleScene extends AbstractScene {
    public SimpleScene() {
        this.displays = new LinkedList<>();
    }

    public SimpleScene(Node... displays) {
        this.displays = new LinkedList<>(Arrays.asList(displays));
    }

    @Override
    public void render() {
        displays.forEach(Node::view);
    }
}
