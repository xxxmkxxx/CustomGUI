package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEvent;

public class EventBus {
    public static final LeftClickEvent LEFT_CLICK_EVENT = new LeftClickEvent();
    public static final HoverEvent HOVER_EVENT = new HoverEvent();
    public static final ResetHoverEvent RESET_HOVER_EVENT = new ResetHoverEvent();
}
