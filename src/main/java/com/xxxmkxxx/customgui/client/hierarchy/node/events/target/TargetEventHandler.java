package com.xxxmkxxx.customgui.client.hierarchy.node.events.target;

public interface TargetEventHandler {
    void addEvent(TargetEvent event);
    void callAllTargetedElements();
}
