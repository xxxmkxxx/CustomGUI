package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AbstractText extends AbstractNode implements Text {
    @Setter
    protected net.minecraft.text.Text text;
    protected int textWidth;
    protected int textHeight;

    protected AbstractText(Pos startPos, net.minecraft.text.Text text) {
        this.text = text;
        this.frame = initFrame(startPos, text);
        this.textWidth = Utils.getTextWidth(text);
        this.textHeight = Utils.getTextHeight();
    }

    private AbstractFrame initFrame(Pos startPos, net.minecraft.text.Text text) {
        return new SimpleFrame(startPos, Utils.getTextWidth(text), Utils.getTextHeight());
    }
}
