package com.xxxmkxxx.customgui.client.hierarchy.node.events.change;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class ChangeEvent extends AbstractNodeEvent<ChangeEventHandler> {
    @Override
    public void callHandler(Integer id, Object ... args) {
        ChangeEventHandler handler;
        if ((handler = handlers.get(id)) != null) handler.onChange();
    }
}
