package com.xxxmkxxx.customgui.client.ui.controls.label;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.ui.controls.text.AbstractText;
import lombok.Getter;

@Getter
public class AbstractLabel extends AbstractNode implements Label {
    protected AbstractNode pointer;
    protected AbstractText text;
    protected AbstractNode.Position position;
    protected int indent;
}
