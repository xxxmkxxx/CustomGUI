package com.xxxmkxxx.customgui.client.hierarchy.node.animation.standard.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.animation.AbstractAnimation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StandardButtonAnimations {
    LEFT_CLICK(new StandardLeftClickButtonAnimation()),
    HOVER(new StandardButtonHoverAnimation());

    private final AbstractAnimation<? extends AbstractNode> animation;

    @SuppressWarnings("unchecked")
    public <N extends AbstractNode> AbstractAnimation<N> getAnimation() {
        return (AbstractAnimation<N>) animation;
    }
}
