package com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractEvent;

public class KeyboardKeyInputEvent extends AbstractEvent<KeyboardKeyInputEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object... args) {
        handlers.get(node).onKeyInput((Integer) args[0]);
    }
}