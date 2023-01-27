package com.xxxmkxxx.customgui.client.hierarchy.window.frame.event.change;

import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.event.AbstractFrameEvent;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.event.FrameEventHandler;

public class ChangeFrameEvent extends AbstractFrameEvent<FrameEventHandler> {
    @Override
    public void callHandler(AbstractFrame frame, Object... args) {
        if (handlers.get(frame) instanceof ChangeFrameEventHandler handler) {
            handler.onChange();
        }
    }
}
