package com.xxxmkxxx.customgui.client.common.event;

public interface Event<T, H> {
    void callHandler(T id, Object ... args);
    void callAllHandlers(Object ... args);
    void addHandler(T id, H handler);
    void removeHandler(T id);
}
