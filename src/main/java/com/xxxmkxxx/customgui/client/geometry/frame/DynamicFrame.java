package com.xxxmkxxx.customgui.client.geometry.frame;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;

public class DynamicFrame extends AbstractFrame {
    public DynamicFrame(int xPos, int yPos, int width, int height, boolean startInCenter) {
        super(xPos, yPos, width, height, startInCenter);
    }

    public DynamicFrame(Pos startPos, int width, int height, boolean startInCenter) {
        super(startPos, width, height, startInCenter);
    }

    public DynamicFrame(Pos startPos, Pos stopPos, boolean startInCenter) {
        super(startPos, stopPos, startInCenter);
    }

    public void setStartPos(Pos startPos) {
        Validator.checkNullObject(startPos);
        this.startPos = startPos;
        updateFields();
    }

    public void setStartPos(int xStartPos, int yStartPos) {
        this.startPos = new Pos(xStartPos, yStartPos);
        updateFields();
    }

    public void setStopPos(Pos stopPos) {
        Validator.checkNullObject(stopPos);
        this.stopPos = stopPos;
        updateFields();
    }

    public void setStopPos(int xStopPos, int yStopPos) {
        this.stopPos = new Pos(xStopPos, yStopPos);
        updateFields();
    }

    private void updateFields() {
        this.width = stopPos.x() - startPos.x();
        this.height = stopPos.y() - startPos.y();
    }
}
