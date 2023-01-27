package com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class KeyboardCharInputEvent extends AbstractNodeEvent<KeyboardCharInputEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object... args) {
        handlers.get(node).onCharInput((Character) args[0]);
    }
}
