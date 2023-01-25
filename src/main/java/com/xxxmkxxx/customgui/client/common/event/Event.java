package com.xxxmkxxx.customgui.client.common.event;

public interface Event<T, H> {
    void callHandler(T type, Object ... args);
    void callAllHandlers(Object ... args);
    void addHandler(T type, H handler);
    void removeHandler(T type);
}
