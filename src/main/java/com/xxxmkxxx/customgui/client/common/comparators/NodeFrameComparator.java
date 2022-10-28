package com.xxxmkxxx.customgui.client.common.comparators;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;

import java.util.Comparator;

public class NodeFrameComparator implements Comparator<AbstractNode> {
    @Override
    public int compare(AbstractNode o1, AbstractNode o2) {
        return o1.getId() - o2.getId();
    }
}
