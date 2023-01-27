package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class ResetHoverEvent extends AbstractNodeEvent<ResetHoverEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        handlers.get(node).onResetHover();
    }
}
