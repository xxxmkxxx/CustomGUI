package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public abstract class AbstractText extends AbstractNode implements Text {
    protected net.minecraft.text.Text text;

    protected AbstractText(Pos startPos, Pos stopPos, net.minecraft.text.Text text, Style style) {
        this.text = text;
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
        this.style = style;

    }

    public void setText(net.minecraft.text.Text text) {
        this.text = text;

        frame.moveStopPos(
                Pos.builder()
                    .coords(
                            frame.getStartPos().getX() + Utils.getTextWidth(text.getString(), style.getFont()),
                               frame.getStopPos().getY()
                    )
                    .build(frame.getLastXPercentValue(), frame.getLastYPercentValue())
        );

        EventBus.CHANGE_EVENT.callHandler(getId());
    }

    public void setText(String text) {
        setText(net.minecraft.text.Text.of(text));
    }
}
