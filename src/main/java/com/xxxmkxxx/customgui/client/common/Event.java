package com.xxxmkxxx.customgui.client.common;

public interface Event<H extends Handler> {
    void callAll();
    void addHandler(H handler);
    void removeHandler(H handler);
}
