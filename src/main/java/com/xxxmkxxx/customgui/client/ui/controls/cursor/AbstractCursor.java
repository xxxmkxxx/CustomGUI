package com.xxxmkxxx.customgui.client.ui.controls.cursor;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public abstract class AbstractCursor extends AbstractNode implements Cursor {
    public AbstractCursor(Pos pos, float width, float height) {
        //gag
        this.frame = SimpleFrame.builder().startPos(pos).widthPercent(0.0f).heightPercent(0.0f).build();
    }

    public void move(float xDistance, float yDistance) {
        frame.moveStartPos(xDistance, yDistance);
        frame.moveStopPos(xDistance, yDistance);
    }
}
