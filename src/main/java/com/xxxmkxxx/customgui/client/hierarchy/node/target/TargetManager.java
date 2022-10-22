package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.NodeSection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.Set;

@RequiredArgsConstructor
public class TargetManager {
    @Getter
    private AbstractNode currentSelection = AbstractNode.EMPTY_NODE;
    @Getter
    private AbstractNode lastSelection = AbstractNode.EMPTY_NODE;

    private final EnumMap<NodeSection, Set<AbstractNode>> sections;

    public void update(int x, int y) {
        lastSelection = currentSelection;
        currentSelection = searchTargetNode(x, y, sections);
    }

    private AbstractNode searchTargetNode(int x, int y, EnumMap<NodeSection, Set<AbstractNode>> sections) {
        for (AbstractNode node : sections.get(defineCursorSection(x, y))) {
            if (node.getFrame().checkPosBelongs(x, y)) return node;
        }

        return AbstractNode.EMPTY_NODE;
    }

    private NodeSection defineCursorSection(int x, int y) {
        for (NodeSection section : NodeSection.values()) {
            if (section.getFrame().checkPosBelongs(x, y)) return section;
        }

        return NodeSection.MIXED;
    }

    public NodeSection defineNodeSection(AbstractNode node) {
        for (NodeSection section : NodeSection.values()) {
            if (section.getFrame().isFrameBelong(node.getFrame())) return section;
        }

        return NodeSection.MIXED;
    }
}
