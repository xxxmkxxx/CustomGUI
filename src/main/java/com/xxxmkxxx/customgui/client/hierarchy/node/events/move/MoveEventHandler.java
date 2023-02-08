package com.xxxmkxxx.customgui.client.hierarchy.node.events.move;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.NodeEventHandler;

public interface MoveEventHandler extends NodeEventHandler {
    void onMove(int x, int y);
}
