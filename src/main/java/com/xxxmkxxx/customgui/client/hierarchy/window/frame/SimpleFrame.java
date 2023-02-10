package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public class SimpleFrame extends AbstractFrame {
    public SimpleFrame(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
    }

    public SimpleFrame(Pos startPos, int width, int height) {
        super(startPos, width, height);
    }

    public SimpleFrame(Pos startPos, Pos stopPos) {
        super(startPos, stopPos);
    }
}