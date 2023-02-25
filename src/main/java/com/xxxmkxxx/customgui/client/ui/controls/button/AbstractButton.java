package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;

@Getter
public abstract class AbstractButton extends AbstractNode implements Button {
    protected final SimpleText text;

    protected AbstractButton(Pos startPos, Pos stopPos, SimpleText text) {
        this.frame = new SimpleFrame(startPos, stopPos);
        this.text = text;
    }
}