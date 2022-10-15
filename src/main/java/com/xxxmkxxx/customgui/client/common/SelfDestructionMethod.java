package com.xxxmkxxx.customgui.client.common;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SelfDestructionMethod {
    @Setter
    private Runnable runnable;

    private static final Runnable runnableEmptyMethod = () -> {};

    public SelfDestructionMethod(Runnable runnable) {
        this.runnable = runnable;
    }

    public void execute() {
        runnable.run();
        runnable = runnableEmptyMethod;
    }
}
