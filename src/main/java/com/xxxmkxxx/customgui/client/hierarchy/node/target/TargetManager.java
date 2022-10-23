package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
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

    private final EnumMap<Section, Set<AbstractNode>> sections;

    public void update(int x, int y) {
        lastSelection = currentSelection;
        currentSelection = searchTargetNode(x, y, sections);
    }

    private AbstractNode searchTargetNode(int x, int y, EnumMap<Section, Set<AbstractNode>> sections) {
        for (AbstractNode node : sections.get(defineCursorSection(x, y))) {
            if (node.getFrame().checkPosBelongs(x, y)) return node;
        }

        return AbstractNode.EMPTY_NODE;
    }

    private Section defineCursorSection(int x, int y) {
        for (Section section : Section.values()) {
            if (section.getFrame().checkPosBelongs(x, y)) return section;
        }

        return Section.MIXED;
    }

    public Section defineNodeSection(AbstractNode node) {
        for (Section section : Section.values()) {
            if (section.getFrame().isFrameBelong(node.getFrame())) return section;
        }

        return Section.MIXED;
    }
}
