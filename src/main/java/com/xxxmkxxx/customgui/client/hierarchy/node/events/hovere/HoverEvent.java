package com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere;

public interface HoverEvent {
    void addHandler(HoverEventHandler handler);
    void callHandlers();
}
