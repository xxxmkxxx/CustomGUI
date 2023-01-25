package com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodevent;

public class KeyboardCharInputEvent extends AbstractNodevent<KeyboardCharInputEventHandler> {
    @Override
    public void callHandler(AbstractNode node, Object... args) {
        handlers.get(node).onCharInput((Character) args[0]);
    }
}
