package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumMap;
import java.util.Set;

@RequiredArgsConstructor
public class TargetManager {
    @Getter
    private AbstractNode currentTarget = AbstractNode.EMPTY_NODE;
    @Getter
    private AbstractNode lastTarget = AbstractNode.EMPTY_NODE;

    private final EnumMap<Section, Set<AbstractNode>> sections;

    public void update(int x, int y) {
        Section section = defineCursorSection(x, y);

        lastTarget = currentTarget;
        currentTarget = searchTargetNode(x, y, sections.get(section));

        if (currentTarget instanceof HoverEventHandler handler && lastTarget != currentTarget) {
            handler.onHover();
        }
    }

    private AbstractNode searchTargetNode(int x, int y, Set<AbstractNode> nodes) {
        for (AbstractNode node : nodes) {
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
