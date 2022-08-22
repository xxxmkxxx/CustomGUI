package com.xxxmkxxx.customgui.client.geometry;

import lombok.Getter;

@Getter
public class Frame {
    private final Pos startPos;
    private final Pos stopPos;
    private final int width;
    private final int height;
    private final boolean startInCenter;

    public Frame(int xPos, int yPos, int width, int height, boolean startInCenter) {
        this.width = width;
        this.height = height;
        this.startInCenter = startInCenter;

        if (startInCenter) {
            this.startPos = new Pos(xPos - width / 2, yPos - height / 2);
            this.stopPos = new Pos(xPos + width / 2, yPos + height / 2);
        } else {
            this.startPos = new Pos(xPos, yPos);
            this.stopPos = new Pos(xPos + width, yPos + height);
        }
    }

    public Frame(Pos startPos, int width, int height, boolean startInCenter) {
        this(startPos.x(), startPos.y(), width, height, startInCenter);
    }
}