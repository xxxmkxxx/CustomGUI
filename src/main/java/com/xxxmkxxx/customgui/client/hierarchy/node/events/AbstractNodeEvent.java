package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNodeEvent<H extends NodeEventHandler> implements NodeEvent<H> {
    protected Map<Integer, H> handlers = new HashMap<>();

    public abstract void callHandler(Integer id, Object ... args);

    @Override
    public void callAllHandlers(Object ... args) {
        handlers.forEach((node, handlers) -> callHandler(node, args));
    }

    @Override
    public void addHandler(Integer id, H handler) {
        handlers.put(id, handler);
    }

    @Override
    public void removeHandler(Integer id) {
        handlers.remove(id);
    }
}
