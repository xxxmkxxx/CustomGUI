package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSectionNodes;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class TargetManager {
    @Getter
    private AbstractNode currentTarget = AbstractNode.EMPTY_NODE;
    @Getter
    private AbstractNode lastTarget = AbstractNode.EMPTY_NODE;
    @Getter @Setter
    private AbstractNode activeNode = AbstractNode.EMPTY_NODE;

    private final WindowSectionNodes windowSectionNodes;

    public TargetManager(WindowSectionNodes windowSectionNodes) {
        this.windowSectionNodes = windowSectionNodes;
    }

    public void update(int x, int y) {
        WindowSection windowSection = defineCursorSection(x, y);

        lastTarget = currentTarget;
        currentTarget = searchTargetNode(x, y, windowSectionNodes.getNodes(windowSection));

        if (lastTarget != currentTarget) {
            if (currentTarget instanceof HoverEventHandler handler) {
                handler.onHover();
            }

            if (lastTarget instanceof ResetHoverEventHandler handler) {
                handler.onResetHover();
            }
        }
    }

    private AbstractNode searchTargetNode(int x, int y, Set<AbstractNode> nodes) {
        AbstractNode targetNode = AbstractNode.EMPTY_NODE;

        for (AbstractNode node : nodes) {
            if (node.getFrame().checkPosBelongs(x, y)) {
                targetNode = node;
                break;
            }
        }

        return targetNode;
    }

    private WindowSection defineCursorSection(int x, int y) {
        WindowSection section = WindowSection.MIXED;

        for (WindowSection windowSection : WindowSection.values()) {
            if (windowSection.getFrame().checkPosBelongs(x, y)) {
                section = windowSection;
                break;
            }
        }

        return section;
    }
}
