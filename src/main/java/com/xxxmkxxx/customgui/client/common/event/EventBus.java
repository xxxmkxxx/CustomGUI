package com.xxxmkxxx.customgui.client.common.event;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character.KeyboardCharInputEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key.KeyboardKeyInputEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.change.ChangeEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEvent;
import com.xxxmkxxx.customgui.client.hierarchy.window.event.resize.ResizeWindowEvent;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.event.change.ChangeFrameEvent;

public class EventBus {
    public static final LeftClickEvent LEFT_CLICK_EVENT = new LeftClickEvent();
    public static final HoverEvent HOVER_EVENT = new HoverEvent();
    public static final ResetHoverEvent RESET_HOVER_EVENT = new ResetHoverEvent();
    public static final ChangeEvent CHANGE_EVENT = new ChangeEvent();

    public static final KeyboardCharInputEvent KEYBOARD_CHAR_INPUT_EVENT = new KeyboardCharInputEvent();
    public static final KeyboardKeyInputEvent KEYBOARD_KEY_INPUT_EVENT = new KeyboardKeyInputEvent();

    public static final ResizeWindowEvent RESIZE_WINDOW_EVENT = new ResizeWindowEvent();

    public static final ChangeFrameEvent CHANGE_FRAME_EVENT = new ChangeFrameEvent();
}
