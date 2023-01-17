package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.inputfield;

import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.field.AbstractField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StandardFieldAnimations {
    LEFT_CLICK(new StandardLeftClickInputFieldAnimation());

    private final AbstractAnimation<AbstractField> animation;
}
