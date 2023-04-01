package com.xxxmkxxx.customgui.client.ui.controls.field.event.send;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class FieldSendEvent extends AbstractNodeEvent<FieldSendEventHandler> {
    @Override
    public void callHandler(Integer id, Object... args) {
        FieldSendEventHandler handler;
        if ((handler = handlers.get(id)) != null) handler.onSend((String) args[0]);
    }
}
