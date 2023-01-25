package com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.NodeEventHandler;

public interface KeyboardCharInputEventHandler extends NodeEventHandler {
    void onCharInput(char symbol);
}
