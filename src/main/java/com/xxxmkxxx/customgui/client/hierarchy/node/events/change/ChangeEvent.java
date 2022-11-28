package com.xxxmkxxx.customgui.client.hierarchy.node.events.change;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractEvent;

public class ChangeEvent extends AbstractEvent<ChangeEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        handlers.get(node).onChange();
    }
}
