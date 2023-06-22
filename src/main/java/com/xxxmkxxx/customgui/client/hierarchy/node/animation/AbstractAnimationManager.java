package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public abstract class AbstractAnimationManager implements AnimationManager {
    public static final AbstractAnimationManager EMPTY_ANIMATION_MANAGER = new AbstractAnimationManager() {
        @Override
        public <N extends AbstractNode> void addSimpleAnimation(N node, String animationName, AbstractAnimation<N> animation, int amountAnimationCycles) {

        }

        @Override
        public <N extends AbstractNode> void addStickyAnimation(N node, String animationName, AbstractAnimation<N> animation, int indexStickyFrame) {

        }

        @Override
        public void deleteStickyAnimation(String animationName) {

        }

        @Override
        public <N extends AbstractNode> void addCyclicAnimation(N node, String animationName, AbstractAnimation<N> animation) {

        }

        @Override
        public void deleteCyclicAnimation(String animationName) {

        }
    };
}
