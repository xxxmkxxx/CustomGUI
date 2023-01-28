package com.xxxmkxxx.customgui.client.common.initializer;

import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

public class Initializer {
    @Getter
    private final Queue<TaskInitializer> tasks = new LinkedList<>();

    public <T extends TaskInitializer> void addTask(T task) {
        tasks.add(task);
    }

    public void initTask(Object ... params) {
        tasks.peek().init(params).run();
    }

    public void initAll(Object ... params) {
        for (int i = 0; i < tasks.size(); i++) {
            initTask(params);
        }
    }

    @FunctionalInterface
    public interface TaskInitializer {
        Runnable init(Object ... params);
    }
}
