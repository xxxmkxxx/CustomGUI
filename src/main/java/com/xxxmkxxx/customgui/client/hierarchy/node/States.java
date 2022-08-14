package com.xxxmkxxx.customgui.client.hierarchy.node;

public interface States extends NodeState<AbstractNode> {
    NodeState<AbstractNode> DISPLAYED = (node, renderer) -> renderer.render(node);
    NodeState<AbstractNode> HIDED = (node, renderer) -> {};
}
