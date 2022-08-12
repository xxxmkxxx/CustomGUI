package com.xxxmkxxx.customgui.client.hierarchy.renderer;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

@FunctionalInterface
public interface NodeRenderer<N extends AbstractNode> {
    void render(N node);
}
