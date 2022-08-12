package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.ui.containers.pane.SimplePane;

public interface State extends NodeState<SimplePane> {
    NodeState<AbstractNode> DISPLAYED = (node, renderer) -> renderer.render(node);
    NodeState<AbstractNode> HIDED = (node, renderer) -> {};
}
