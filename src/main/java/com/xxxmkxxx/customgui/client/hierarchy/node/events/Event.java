package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public interface Event<H extends EventHandler> {
    void callHandler(AbstractNode node, Object ... args);
    void callAllHandlers(Object ... args);
    void addHandler(AbstractNode node, H handler);
    void removeHandler(AbstractNode node);
}
