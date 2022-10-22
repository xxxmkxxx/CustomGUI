package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.NodeSection;
import lombok.Getter;

import java.util.stream.Stream;

public class TargetManager {
    @Getter
    private AbstractNode currentSelection;
    @Getter
    private AbstractNode lastSelection;

    public void setCurrentSelection(AbstractNode currentSelection) {
        this.lastSelection = this.currentSelection;
        this.currentSelection = currentSelection;
    }

    public NodeSection defineNodeSection(AbstractNode node) {
        NodeSection[] nodeSections = NodeSection.values();

        for (int i = 0; i < nodeSections.length; i++) {
            if (nodeSections[i].getFrame().isFrameBelong(node.getFrame())) return nodeSections[i];
        }

        return NodeSection.MIXED;
    }
}
