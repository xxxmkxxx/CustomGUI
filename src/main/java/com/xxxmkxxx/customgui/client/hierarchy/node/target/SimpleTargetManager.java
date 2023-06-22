package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSectionNodes;

import java.util.Set;

public class SimpleTargetManager extends AbstractTargetManager {
    private final WindowSectionNodes windowSectionNodes;

    public SimpleTargetManager(WindowSectionNodes windowSectionNodes) {
        this.windowSectionNodes = windowSectionNodes;
    }

    @Override
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
