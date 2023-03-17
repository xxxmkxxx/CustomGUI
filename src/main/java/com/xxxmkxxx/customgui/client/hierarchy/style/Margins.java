package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Margins implements Cloneable {
    @Builder.Default
    private float top = 0.0f;
    @Builder.Default
    private float bottom = 0.0f;
    @Builder.Default
    private float right = 0.0f;
    @Builder.Default
    private float left = 0.0f;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Margins(top, bottom, right, left);
    }
}
