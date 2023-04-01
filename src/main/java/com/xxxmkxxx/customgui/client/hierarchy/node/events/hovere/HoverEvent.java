package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class HoverEvent extends AbstractNodeEvent<HoverEventHandler> {
    @Override
    public void callHandler(Integer id, Object ... args) {
        handlers.get(id).onHover();
    }
}
