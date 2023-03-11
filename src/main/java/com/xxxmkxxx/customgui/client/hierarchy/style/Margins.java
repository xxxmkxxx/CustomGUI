package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Margins implements Cloneable {
    @Builder.Default
    private int top = 0;
    @Builder.Default
    private int bottom = 0;
    @Builder.Default
    private int right = 0;
    @Builder.Default
    private int left = 0;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Margins(top, bottom, right, left);
    }
}
