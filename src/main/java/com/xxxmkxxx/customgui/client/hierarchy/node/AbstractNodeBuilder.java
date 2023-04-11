package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

import java.util.function.Supplier;

public abstract class AbstractNodeBuilder<N extends AbstractNode> implements NodeBuilder<N> {
    protected Pos startPos;
    protected Pos stopPos;
    protected Style style;
    protected float width;
    protected float height;
    protected float widthPercent;
    protected float heightPercent;

    protected AbstractNodeBuilder() {
        this.startPos = Pos.defaultPos();
        this.style = Style.defaultStyle();
        this.width = height = 5;
        this.widthPercent = heightPercent = Float.MIN_VALUE;
    }

    @Override
    public AbstractNodeBuilder<N> startPos(Pos pos) {
        try {
            this.startPos = (Pos) pos.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    @Override
    public AbstractNodeBuilder<N> stopPos(Pos pos) {
        try {
            this.stopPos = (Pos) pos.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    @Override
    public AbstractNodeBuilder<N> positions(Pos startPos, Pos stopPos) {
        startPos(startPos);
        stopPos(stopPos);
        return this;
    }

    @Override
    public AbstractNodeBuilder<N> positions(AbstractFrame frame) {
        positions(frame.getStartPos(), frame.getStopPos());
        return this;
    }

    @Override
    public AbstractNodeBuilder<N> sizes(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public AbstractNodeBuilder<N> sizesPercent(float widthPercent, float heightPercent) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        return this;
    }

    @Override
    public AbstractNodeBuilder<N> style(Style style) {
        try {
            this.style = (Style) style.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    protected Pos createStopPos() {
        Pos pos;

        if (stopPos != null) return stopPos;

        if (widthPercent == Float.MIN_VALUE) {
            pos = Pos.builder()
                    .coords(startPos.getX() + width, startPos.getY() + height)
                    .proportionBy(startPos.getProportionBy())
                    .build(startPos.getXPercentValue(), startPos.getYPercentValue());
        } else {
            pos = Pos.builder()
                    .relativeCoords(
                            startPos.getXIndentPercent() + widthPercent,
                            startPos.getYIndentPercent() + heightPercent
                    )
                    .proportionBy(startPos.getProportionBy())
                    .build(startPos.getXPercentValue(), startPos.getYPercentValue());
        }

        return pos;
    }

    @Override
    public N build(Supplier<N> factory) {
        return factory.get();
    }

    public abstract N build();
}
