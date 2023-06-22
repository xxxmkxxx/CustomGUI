package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.AbstractTargetManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class KeyboardManager {
    private final AbstractTargetManager simpleTargetManager;
    @Getter @Setter
    private boolean isActive = false;

    public void onCharInput(char symbol) {
        if (isActive) {
            EventBus.KEYBOARD_CHAR_INPUT_EVENT.callHandler(simpleTargetManager.getActiveNode().getId(), symbol);
        }
    }

    public void onKeyDown(int keyCode) {
        if (isActive) {
            EventBus.KEYBOARD_KEY_INPUT_EVENT.callHandler(simpleTargetManager.getActiveNode().getId(), keyCode);
        }
    }
}
