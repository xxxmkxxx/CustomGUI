package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AbstractField extends AbstractNode implements Field {
    @Setter
    protected int textColor;
    protected StringBuilder text = new StringBuilder();
    protected int textSize;
}
