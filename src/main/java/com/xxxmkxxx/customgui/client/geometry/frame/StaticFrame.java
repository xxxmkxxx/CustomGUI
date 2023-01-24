package com.xxxmkxxx.customgui.client.geometry.frame;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import lombok.Getter;

@Getter
public class StaticFrame extends AbstractFrame {
    public StaticFrame(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
    }

    public StaticFrame(Pos startPos, int width, int height, boolean startInCenter) {
        super(startPos, width, height);
    }

    public StaticFrame(Pos startPos, Pos stopPos, boolean startInCenter) {
        super(startPos, stopPos);
    }
}