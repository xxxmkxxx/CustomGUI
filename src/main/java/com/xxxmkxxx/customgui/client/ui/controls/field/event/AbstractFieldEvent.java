package com.xxxmkxxx.customgui.client.ui.controls.field.event;

import com.xxxmkxxx.customgui.client.common.event.Event;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFieldEvent<H extends FieldEventHandler> implements Event<AbstractNode, H> {
    protected Map<AbstractNode, H> handlers = new HashMap<>();

    public abstract void callHandler(AbstractNode node, Object ... args);

    @Override
    public void callAllHandlers(Object ... args) {
        handlers.forEach((node, handlers) -> callHandler(node, args));
    }

    @Override
    public void addHandler(AbstractNode node, H handler) {
        handlers.put(node, handler);
    }

    @Override
    public void removeHandler(AbstractNode node) {
        handlers.remove(node);
    }
}
