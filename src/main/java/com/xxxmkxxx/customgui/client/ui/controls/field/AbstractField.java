package com.xxxmkxxx.customgui.client.ui.controls.field;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AbstractField extends AbstractNode implements Field {
    protected StringBuilder textBuilder = new StringBuilder();
}
