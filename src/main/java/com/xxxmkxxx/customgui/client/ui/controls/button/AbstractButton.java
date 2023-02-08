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

    public AbstractButton(Pos startPos, String buttonText) {
        this.text = SimpleText.builder()
                .text(buttonText)
                .pos(startPos)
                .build();
        this.frame = new SimpleFrame(
                startPos,
                text.getFrame().getStopPos()
        );
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        text.initRenderer(type);
    }

    @Override
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        text.scaling(widthScaleValue, heightScaleValue);
    }
}