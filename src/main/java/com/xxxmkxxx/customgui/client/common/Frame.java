package com.xxxmkxxx.customgui.client.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Frame {
    private final Pos startPos;
    private final Pos stopPos;

    public Frame(int xPos, int yPos, int width, int height, boolean startInCenter) {
        if (startInCenter) {
            this.startPos = new Pos(xPos - width / 2, yPos - height / 2);
            this.stopPos = new Pos(xPos + width / 2, yPos + height / 2);
        } else {
            this.startPos = new Pos(xPos, yPos);
            this.stopPos = new Pos(xPos + width, yPos + height);
        }
    }
}