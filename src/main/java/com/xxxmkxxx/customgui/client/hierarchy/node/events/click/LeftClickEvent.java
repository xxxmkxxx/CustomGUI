package com.xxxmkxxx.customgui.client.hierarchy.node.events.click;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractEvent;

public class LeftClickEvent extends AbstractEvent<LeftClickEventHandler> {
    @Override
    public void callHandler(AbstractNode node) {
        handlers.get(node).onLeftClick();
    }
}
