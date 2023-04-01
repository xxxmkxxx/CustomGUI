package com.xxxmkxxx.customgui.client.ui.controls.field.event.send;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.ui.controls.field.event.AbstractFieldEvent;

public class FieldSendEvent extends AbstractFieldEvent<FieldSendEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object... args) {
        FieldSendEventHandler handler;
        if ((handler = handlers.get(node)) != null) handler.onSend((String) args[0]);
    }
}
