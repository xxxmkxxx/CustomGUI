package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Style {
    private Shadow shadow = Shadow.builder().build();
    private Indent indent = Indent.builder().build();
}
