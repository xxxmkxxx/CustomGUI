package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.ui.animations.button.ButtonAnimations;
import lombok.Getter;

@Getter
public abstract class AbstractButton extends AbstractNode implements Button {
    protected final ButtonAnimations animations = new ButtonAnimations(this);
}
