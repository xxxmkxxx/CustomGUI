package com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class KeyboardKeyInputEvent extends AbstractNodeEvent<KeyboardKeyInputEventHandler> {
    @Override
    public void callHandler(Integer id, Object... args) {
        KeyboardKeyInputEventHandler handler;
        if ((handler = handlers.get(id)) != null) handler.onKeyInput((Integer) args[0]);
    }
}
