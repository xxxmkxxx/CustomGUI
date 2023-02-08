package com.xxxmkxxx.customgui.client.ui.controls.text;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;

@Getter
public class AbstractText extends AbstractNode implements Text {
    protected Pos startPos;
    protected net.minecraft.text.Text text;
    protected int textWidth;
    protected int textHeight;

    protected AbstractText(Pos startPos, net.minecraft.text.Text text) {
        this.startPos = startPos;
        this.text = text;
        this.textWidth = Utils.getTextWidth(text);
        this.textHeight = Utils.getTextHeight();
        this.frame = new SimpleFrame(startPos, textWidth, textHeight);
    }

    public void setText(net.minecraft.text.Text text) {
        this.text = text;
        textWidth = Utils.getTextWidth(text);
        double lastWidthScaleValue = frame.getLastWidthScaleValue();
        double lastHeightScaleValue = frame.getLastHeightScaleValue();
        frame = new SimpleFrame(frame.getInitialStartPos(), textWidth, textHeight);
        frame.scaling(lastWidthScaleValue, lastHeightScaleValue);
        EventBus.CHANGE_EVENT.callHandler(this);
    }

    public void setText(String text) {
        setText(net.minecraft.text.Text.of(text));
    }

    @Override
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale((float) widthScaleValue, (float) heightScaleValue, 1);
        style.setMatrixStack(matrixStack);
    }
}
