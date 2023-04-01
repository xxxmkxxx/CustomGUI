package com.xxxmkxxx.customgui.client.ui.controls.field.event.send;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.NodeEventHandler;

public interface FieldSendEventHandler extends NodeEventHandler {
    void onSend(String text);
}
