package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public abstract class AbstractCursor extends AbstractNode implements Cursor {
    public AbstractCursor(Pos startPos, Pos stopPos, Style style) {
        this.style = style;
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
    }

    public void move(float xDistance, float yDistance) {
        frame.moveStartPos(xDistance, yDistance);
        frame.moveStopPos(xDistance, yDistance);
    }

    public void move(Pos pos) {
        frame.moveStartPos(pos);
        frame.moveStopPos(pos);
    }
}
