package com.xxxmkxxx.customgui.client.hierarchy.node.events.click;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class LeftClickEvent extends AbstractNodeEvent<LeftClickEventHandler> {
    @Override
    public void callHandler(Integer id, Object ... args) {
        handlers.get(id).onLeftClick();
    }
}
