package com.xxxmkxxx.customgui.client.hierarchy.node.events.move;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.AbstractNodeEvent;

public class MoveEvent extends AbstractNodeEvent<MoveEventHandler> {
    @Override
    public void callHandler(Integer id, Object... args) {
        handlers.get(id).onMove((Integer) args[0], (Integer) args[1]);
    }
}
