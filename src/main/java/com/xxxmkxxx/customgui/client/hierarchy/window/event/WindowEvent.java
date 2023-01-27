package com.xxxmkxxx.customgui.client.hierarchy.window.event;

import com.xxxmkxxx.customgui.client.common.event.Event;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;

public interface WindowEvent<H extends WindowEventHandler> extends Event<Window, H> {}