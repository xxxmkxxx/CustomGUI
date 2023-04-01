package com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class KeyboardCharInputEvent extends AbstractNodeEvent<KeyboardCharInputEventHandler> {
    @Override
    public void callHandler(Integer id, Object... args) {
        KeyboardCharInputEventHandler handler;
        if ((handler = handlers.get(id)) != null) handler.onCharInput((Character) args[0]);
    }
}
