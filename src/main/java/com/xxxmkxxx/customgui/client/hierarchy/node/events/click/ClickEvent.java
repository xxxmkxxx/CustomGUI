package com.xxxmkxxx.customgui.client.hierarchy.node.events.click;

public interface ClickEvent<H extends ClickEventHandler> {
    void addHandler(H handler);
    void callHandler(H handler);
    void callAllHandlers();
}
