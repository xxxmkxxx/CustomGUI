package com.xxxmkxxx.customgui.client.hierarchy.node.keyboard;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractKeyboardManager implements KeyboardManager {
    public static final AbstractKeyboardManager EMPTY_KEYBOARD_MANAGER = new AbstractKeyboardManager() {
        @Override
        public void onCharInput(char symbol) {}

        @Override
        public void onKeyDown(int keyCode) {}
    };
    @Getter @Setter
    protected boolean isActive = false;
}
