package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class HoverEvent extends AbstractNodeEvent<HoverEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        handlers.get(node).onHover();
    }
}
