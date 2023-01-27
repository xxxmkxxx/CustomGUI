package com.xxxmkxxx.customgui.client.hierarchy.node.events.click;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class LeftClickEvent extends AbstractNodeEvent<LeftClickEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        handlers.get(node).onLeftClick();
    }
}
