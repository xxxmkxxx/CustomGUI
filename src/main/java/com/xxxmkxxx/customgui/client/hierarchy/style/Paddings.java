package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Paddings implements Cloneable {
    @Builder.Default
    private float top = 0.0f;
    @Builder.Default
    private float bottom = 0.0f;
    @Builder.Default
    private float right = 0.0f;
    @Builder.Default
    private float left = 0.0f;

    @Override
    public Paddings clone() {
        return new Paddings(top, bottom, right, left);
    }
}
