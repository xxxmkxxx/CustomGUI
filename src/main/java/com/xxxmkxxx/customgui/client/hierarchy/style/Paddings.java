package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Paddings implements Cloneable {
    @Builder.Default
    private int top = 0;
    @Builder.Default
    private int bottom = 0;
    @Builder.Default
    private int right = 0;
    @Builder.Default
    private int left = 0;

    @Override
    public Paddings clone() {
        return new Paddings(top, bottom, right, left);
    }
}
