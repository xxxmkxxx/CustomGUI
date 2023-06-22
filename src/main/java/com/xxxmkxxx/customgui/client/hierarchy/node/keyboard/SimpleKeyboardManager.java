package com.xxxmkxxx.customgui.client.hierarchy.node.keyboard;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.AbstractTargetManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleKeyboardManager extends AbstractKeyboardManager {
    private final AbstractTargetManager targetManager;

    @Override
    public void onCharInput(char symbol) {
        if (isActive) {
            EventBus.KEYBOARD_CHAR_INPUT_EVENT.callHandler(targetManager.getActiveNode().getId(), symbol);
        }
    }

    @Override
    public void onKeyDown(int keyCode) {
        if (isActive) {
            EventBus.KEYBOARD_KEY_INPUT_EVENT.callHandler(targetManager.getActiveNode().getId(), keyCode);
        }
    }
}
