package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StandardButtonAnimations {
    LEFT_CLICK(new StandardLeftClickButtonAnimation());

    private final AbstractAnimation<AbstractButton> animation;
}
