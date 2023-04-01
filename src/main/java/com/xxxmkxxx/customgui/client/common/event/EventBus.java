package com.xxxmkxxx.customgui.client.common.event;

import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.character.KeyboardCharInputEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.input.key.KeyboardKeyInputEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.change.ChangeEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.move.MoveEvent;
import com.xxxmkxxx.customgui.client.hierarchy.window.event.resize.ResizeWindowEvent;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.event.change.ChangeFrameEvent;
import com.xxxmkxxx.customgui.client.ui.controls.field.event.send.FieldSendEvent;

public class EventBus {
    //Node events
    public static final LeftClickEvent LEFT_CLICK_EVENT = new LeftClickEvent();
    public static final HoverEvent HOVER_EVENT = new HoverEvent();
    public static final ResetHoverEvent RESET_HOVER_EVENT = new ResetHoverEvent();
    public static final ChangeEvent CHANGE_EVENT = new ChangeEvent();
    public static final MoveEvent MOVE_EVENT = new MoveEvent();
    public static final KeyboardCharInputEvent KEYBOARD_CHAR_INPUT_EVENT = new KeyboardCharInputEvent();
    public static final KeyboardKeyInputEvent KEYBOARD_KEY_INPUT_EVENT = new KeyboardKeyInputEvent();

    //Field events
    public static final FieldSendEvent FIELD_SEND_EVENT = new FieldSendEvent();

    //Window events
    public static final ResizeWindowEvent RESIZE_WINDOW_EVENT = new ResizeWindowEvent();

    //Frame events
    public static final ChangeFrameEvent CHANGE_FRAME_EVENT = new ChangeFrameEvent();
}
