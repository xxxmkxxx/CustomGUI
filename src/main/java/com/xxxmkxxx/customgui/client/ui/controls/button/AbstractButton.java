package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.text.AbstractText;
import lombok.Getter;

@Getter
public abstract class AbstractButton extends AbstractNode implements Button {
    protected final AbstractText text;

    public AbstractButton(Pos startPos, AbstractText text) {
        this.text = text;
        this.frame = initFrame(startPos, text);
    }

    private AbstractFrame initFrame(Pos startPos, AbstractText text) {
        return new SimpleFrame(
                startPos,
                text.getTextWidth() + text.getStyle().getIndent().getLeft() + text.getStyle().getIndent().getRight(),
                text.getTextHeight() + text.getStyle().getIndent().getTop() + text.getStyle().getIndent().getBottom()
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