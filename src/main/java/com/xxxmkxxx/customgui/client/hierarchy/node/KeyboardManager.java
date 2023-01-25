package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class KeyboardManager {
    private final TargetManager targetManager;
    @Getter @Setter
    private boolean isActive = false;

    public void onCharInput(char symbol) {
        if (isActive) {
            EventBus.KEYBOARD_CHAR_INPUT_EVENT.callHandler(targetManager.getActiveNode(), symbol);
        }
    }

    public void onKeyDown(int keyCode) {
        if (isActive) {
            EventBus.KEYBOARD_KEY_INPUT_EVENT.callHandler(
                    targetManager.getActiveNode(),
                    keyCode
            );
        }
    }
}
