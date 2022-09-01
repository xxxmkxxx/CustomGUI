package com.xxxmkxxx.customgui.client.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SelfDestructionMethod {
    private Runnable runnable;
    private static final Runnable emptyMethod = () -> {};

    public void execute() {
        runnable.run();
        runnable = emptyMethod;
    }
}
