package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public abstract class AbstractCursor extends AbstractNode implements Cursor {
    protected int width;
    protected int height;

    public AbstractCursor(Pos pos, int width, int height) {
        this.width = width;
        this.height = height;
        this.frame = new SimpleFrame(pos, width, height);
    }

    public void move(int xDistance, int yDistance) {
        frame.moveStartPos(xDistance, yDistance);
        frame.moveStopPos(xDistance, yDistance);
    }
}
