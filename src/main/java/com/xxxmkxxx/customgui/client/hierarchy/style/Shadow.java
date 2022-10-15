package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Shadow {
    private Direction direction;

    public enum Direction {
        TOP, BOTTOM, RIGHT, LEFT;
    }
}
