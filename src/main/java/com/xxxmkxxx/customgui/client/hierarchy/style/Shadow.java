package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class Shadow implements Cloneable {
    private Color color;
    private Direction direction;

    @Builder
    public Shadow() {
         this.color = Color.builder().build();
         this.direction = Direction.RIGHT;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Shadow((Color) color.clone(), direction);
    }

    public enum Direction {
        TOP, BOTTOM, RIGHT, LEFT;
    }
}
