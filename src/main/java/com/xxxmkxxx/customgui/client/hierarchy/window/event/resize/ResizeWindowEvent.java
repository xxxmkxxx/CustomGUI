package com.xxxmkxxx.customgui.client.hierarchy.window.event.resize;

import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.event.AbstractWindowEvent;

public class ResizeWindowEvent extends AbstractWindowEvent<ResizeWindowEventHandler> {
    @Override
    public void callHandler(Window window, Object... args) {
        net.minecraft.client.util.Window win = (net.minecraft.client.util.Window) args[0];
        handlers.get(window).onResize(win.getWidth(), win.getScaledWidth(), win.getHeight(), win.getScaledHeight());
    }
}
