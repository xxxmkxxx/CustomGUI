package com.xxxmkxxx.customgui.client.hierarchy.window.event.resize;

import com.xxxmkxxx.customgui.client.hierarchy.window.event.WindowEventHandler;

public interface ResizeWindowEventHandler extends WindowEventHandler {
    void onResize(int windowWidth, int windowHeight, int scaledWindowWidth, int scaledWindowHeight);
}
