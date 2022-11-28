package com.xxxmkxxx.customgui.client.hierarchy.node.events;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character.KeyboardCharInputEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key.KeyboardKeyInputEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.change.ChangeEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEvent;

public class EventBus {
    public static final LeftClickEvent LEFT_CLICK_EVENT = new LeftClickEvent();
    public static final HoverEvent HOVER_EVENT = new HoverEvent();
    public static final ResetHoverEvent RESET_HOVER_EVENT = new ResetHoverEvent();
    public static final ChangeEvent CHANGE_EVENT = new ChangeEvent();
    public static final KeyboardCharInputEvent KEYBOARD_CHAR_INPUT_EVENT = new KeyboardCharInputEvent();
    public static final KeyboardKeyInputEvent KEYBOARD_KEY_INPUT_EVENT = new KeyboardKeyInputEvent();
}
