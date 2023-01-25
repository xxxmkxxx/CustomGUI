package com.xxxmkxxx.customgui.client.hierarchy.node.events.click;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodevent;

public class LeftClickEvent extends AbstractNodevent<LeftClickEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        handlers.get(node).onLeftClick();
    }
}
