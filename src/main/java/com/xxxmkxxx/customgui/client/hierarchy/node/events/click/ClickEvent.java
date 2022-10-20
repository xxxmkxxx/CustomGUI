package com.xxxmkxxx.customgui.client.hierarchy.node.events.click;

public interface ClickEvent {
    void addEvent(ClickEventHandler event);
    void callAllClickedEvents();
}
