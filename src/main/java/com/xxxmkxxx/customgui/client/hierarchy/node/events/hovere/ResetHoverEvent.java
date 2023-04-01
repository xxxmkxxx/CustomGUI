package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class ResetHoverEvent extends AbstractNodeEvent<ResetHoverEventHandler> {
    @Override
    public void callHandler(Integer id, Object ... args) {
        handlers.get(id).onResetHover();
    }
}
