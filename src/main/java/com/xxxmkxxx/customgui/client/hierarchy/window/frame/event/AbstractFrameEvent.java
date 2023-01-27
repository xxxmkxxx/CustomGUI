package com.xxxmkxxx.customgui.client.hierarchy.window.frame.event;

import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFrameEvent<H extends FrameEventHandler> implements FrameEvent<H> {
    protected Map<AbstractFrame, H> handlers = new HashMap<>();

    public abstract void callHandler(AbstractFrame frame, Object ... args);

    @Override
    public void callAllHandlers(Object ... args) {
        handlers.forEach((frame, handlers) -> callHandler(frame, args));
    }

    @Override
    public void addHandler(AbstractFrame frame, H handler) {
        handlers.put(frame, handler);
    }

    @Override
    public void removeHandler(AbstractFrame frame) {
        handlers.remove(frame);
    }
}
