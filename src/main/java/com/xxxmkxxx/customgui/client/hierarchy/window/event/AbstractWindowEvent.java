package com.xxxmkxxx.customgui.client.hierarchy.window.event;

import com.xxxmkxxx.customgui.client.hierarchy.window.Window;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWindowEvent<H extends WindowEventHandler> implements WindowEvent<H> {
    protected Map<Window, H> handlers = new HashMap<>();

    public abstract void callHandler(Window window, Object ... args);

    @Override
    public void callAllHandlers(Object ... args) {
        handlers.forEach((node, handlers) -> callHandler(node, args));
    }

    @Override
    public void addHandler(Window window, H handler) {
        handlers.put(window, handler);
    }

    @Override
    public void removeHandler(Window window) {
        handlers.remove(window);
    }
}
