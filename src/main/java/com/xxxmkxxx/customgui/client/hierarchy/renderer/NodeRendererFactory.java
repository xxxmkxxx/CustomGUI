package com.xxxmkxxx.customgui.client.hierarchy.renderer;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public interface NodeRendererFactory<N extends AbstractNode> {
    NodeRenderer<N> create(RendererType type);
}
