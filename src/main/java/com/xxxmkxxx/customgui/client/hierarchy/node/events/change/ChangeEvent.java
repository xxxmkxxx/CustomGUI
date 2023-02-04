package com.xxxmkxxx.customgui.client.hierarchy.node.events.change;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class ChangeEvent extends AbstractNodeEvent<ChangeEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object ... args) {
        ChangeEventHandler handler;
        if ((handler = handlers.get(node)) != null) handler.onChange();
    }
}
