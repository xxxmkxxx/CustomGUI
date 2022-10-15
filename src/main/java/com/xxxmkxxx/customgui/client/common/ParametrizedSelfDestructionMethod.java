package com.xxxmkxxx.customgui.client.common;

import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@NoArgsConstructor
public class ParametrizedSelfDestructionMethod <T> {
    private Consumer<T> consumer;

    private final Consumer<T> consumerEmptyMethod = object -> {};

    public void setAction(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public void execute(T object) {
        consumer.accept(object);
        consumer = consumerEmptyMethod;
    }
}
