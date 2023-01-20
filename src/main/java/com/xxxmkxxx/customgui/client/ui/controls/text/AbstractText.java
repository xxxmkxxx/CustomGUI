package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AbstractText extends AbstractNode implements Text {
    @Setter
    protected net.minecraft.text.Text text = net.minecraft.text.Text.of("");
    protected int textSize;
    protected Pos startPos = new Pos(5, 5);
}
