package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractTargetManager implements TargetManager {
    public static final AbstractTargetManager EMPTY_TARGET_MANAGER = new AbstractTargetManager() {
        @Override
        public void update(int x, int y) {}
    };
    @Getter
    protected AbstractNode currentTarget = AbstractNode.EMPTY_NODE;
    @Getter
    protected AbstractNode lastTarget = AbstractNode.EMPTY_NODE;
    @Getter @Setter
    protected AbstractNode activeNode = AbstractNode.EMPTY_NODE;
}
