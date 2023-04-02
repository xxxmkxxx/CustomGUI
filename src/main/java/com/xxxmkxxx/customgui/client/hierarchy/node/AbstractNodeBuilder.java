package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

import java.util.function.Supplier;

public abstract class AbstractNodeBuilder<N extends AbstractNode> implements NodeBuilder<N> {
    protected Pos startPos;
    protected Pos stopPos;
    protected Style style;

    protected AbstractNodeBuilder() {
        this.startPos = Pos.defaultPos();
        this.style = Style.defaultStyle();
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
    public AbstractNodeBuilder<N> style(Style style) {
        try {
            this.style = (Style) style.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public N build(Supplier<N> factory) {
        return factory.get();
    }

    public abstract N build();
}
