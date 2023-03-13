package com.xxxmkxxx.customgui.client.ui.controls.label;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.layout.Position;
import lombok.Getter;

@Getter
public abstract class AbstractLabel extends AbstractNode implements Label {
    protected AbstractNode pointer;
    protected Position position;
}
