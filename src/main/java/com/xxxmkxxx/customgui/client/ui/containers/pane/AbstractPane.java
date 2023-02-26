package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public abstract class AbstractPane extends AbstractNode implements Pane {
    public AbstractPane(Pos startPos, int width, int height) {
        //gag
        this.frame = SimpleFrame.builder().startPos(startPos).widthPercent(0.0).heightPercent(0.0).build();
    }
}