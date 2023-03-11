package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public class AbstractText extends AbstractNode implements Text {
    protected net.minecraft.text.Text text;
    protected double textWidth;
    protected double textHeight;

    protected AbstractText(Pos startPos, Pos stopPos, net.minecraft.text.Text text) {
        this.text = text;
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
    }

    public void setText(net.minecraft.text.Text text) {
        this.text = text;
        double lastXPercentValue = frame.getLastXPercentValue();
        double lastYPercentValue = frame.getLastYPercentValue();
        frame = SimpleFrame.builder()
                .startPos(frame.getInitialStartPos())
                .widthPercent(textWidth)
                .heightPercent(textHeight)
                .build();
        frame.scaling(lastXPercentValue, lastYPercentValue);
        EventBus.CHANGE_EVENT.callHandler(this);
    }

    public void setText(String text) {
        setText(net.minecraft.text.Text.of(text));
    }
}
