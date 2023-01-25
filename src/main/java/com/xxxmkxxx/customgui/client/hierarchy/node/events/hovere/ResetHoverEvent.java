package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodevent;

public class ResetHoverEvent extends AbstractNodevent<ResetHoverEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        handlers.get(node).onResetHover();
    }
}
