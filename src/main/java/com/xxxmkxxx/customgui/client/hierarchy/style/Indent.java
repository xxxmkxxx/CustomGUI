package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Indent implements Cloneable {
    @Builder.Default
    private int top = 1;
    @Builder.Default
    private int bottom = 1;
    @Builder.Default
    private int right = 1;
    @Builder.Default
    private int left = 1;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Indent(top, bottom, right, left);
    }
}
