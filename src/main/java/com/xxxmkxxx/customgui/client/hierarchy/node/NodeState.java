package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;

@FunctionalInterface
public interface NodeState<N extends AbstractNode> {
    void execute(N node, NodeRenderer<N> renderer);
}
