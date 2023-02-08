package com.xxxmkxxx.customgui.client.hierarchy.node.events.move;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class MoveEvent extends AbstractNodeEvent<MoveEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object... args) {
        handlers.get(node).onMove((Integer) args[0], (Integer) args[1]);
    }
}
