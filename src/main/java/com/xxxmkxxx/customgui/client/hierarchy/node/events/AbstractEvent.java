package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEvent<H extends EventHandler> implements Event<H> {
    protected Map<AbstractNode, H> handlers = new HashMap<>();

    public abstract void callHandler(AbstractNode node, Object ... args);

    @Override
    public void callAllHandlers() {
        handlers.forEach((node, handlers) -> callHandler(node));
    }

    @Override
    public void addHandler(AbstractNode node, H handler) {
        handlers.put(node, handler);
    }
}
