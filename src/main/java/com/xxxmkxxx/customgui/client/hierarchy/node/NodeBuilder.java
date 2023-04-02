package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

import java.util.function.Supplier;

public interface NodeBuilder<N> {
    NodeBuilder<N> startPos(Pos pos);

    NodeBuilder<N> stopPos(Pos pos);

    NodeBuilder<N> positions(Pos startPos, Pos stopPos);

    NodeBuilder<N> positions(AbstractFrame frame);

    NodeBuilder<N> style(Style style);

    N build(Supplier<N> factory);
}
