package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Shadow implements Cloneable {
    private Color color;
    private Direction direction;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Shadow((Color) color.clone(), direction);
    }

    public enum Direction {
        TOP, BOTTOM, RIGHT, LEFT;
    }
}
