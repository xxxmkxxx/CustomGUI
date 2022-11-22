package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractEvent;

public class ResetHoverEvent extends AbstractEvent<ResetHoverEventHandler> {
    @Override
    public void callHandler(AbstractNode node) {
        handlers.get(node).onResetHover();
    }
}
