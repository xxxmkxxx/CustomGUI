package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public class SimpleFrame extends AbstractFrame {
    public SimpleFrame(int xPos, int yPos, int width, int height, double xPercentValue, double yPercentValue) {
        super(xPos, yPos, width, height, xPercentValue, yPercentValue);
    }

    public SimpleFrame(Pos startPos, double widthPercents, double heightPercents) {
        super(startPos, widthPercents, heightPercents);
    }

    public SimpleFrame(Pos startPos, Pos stopPos) {
        super(startPos, stopPos);
    }
}