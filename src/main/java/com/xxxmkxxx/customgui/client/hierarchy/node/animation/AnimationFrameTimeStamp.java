package com.xxxmkxxx.customgui.client.hierarchy.node.animation;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

public record AnimationFrameTimeStamp<N extends AbstractNode>(long timeUnit, AnimationFrame<N> frame) {}
