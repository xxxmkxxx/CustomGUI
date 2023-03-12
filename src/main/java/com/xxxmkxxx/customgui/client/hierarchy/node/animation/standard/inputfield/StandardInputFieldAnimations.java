package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.inputfield;

import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import com.xxxmkxxx.customgui.client.ui.controls.field.NoneExpandableInputField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StandardInputFieldAnimations {
    LEFT_CLICK(new StandardLeftClickInputFieldAnimation());

    private final AbstractAnimation<NoneExpandableInputField> animation;
}
