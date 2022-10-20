package com.xxxmkxxx.customgui.client.hierarchy.node.events.selection;

public interface SelectionEvent {
    void addEvent(SelectionEventHandler event);
    void callAllSelectionElements();
}
