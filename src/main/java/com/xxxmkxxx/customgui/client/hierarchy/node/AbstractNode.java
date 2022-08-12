package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;

@Getter
public abstract class AbstractNode implements Node {
    protected NodeState<AbstractNode> state;
    protected NodeRenderer renderer;

    public abstract void initRenderer(RendererType type);
}
