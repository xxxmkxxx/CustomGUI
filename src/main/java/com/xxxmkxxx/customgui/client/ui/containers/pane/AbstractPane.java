package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public abstract class AbstractPane extends AbstractNode implements Pane {
    public AbstractPane(Pos startPos, Pos stopPos) {
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
    }
}