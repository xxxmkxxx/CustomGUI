package com.xxxmkxxx.customgui.client.common.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InventoryType {
    EMPTY(0), ENTITY(1), BLOCK(2);

    private final int number;

    public static InventoryType of(int number) {
        switch (number) {
            case 1: return ENTITY;
            case 2: return BLOCK;
            default: return EMPTY;
        }
    }
}
