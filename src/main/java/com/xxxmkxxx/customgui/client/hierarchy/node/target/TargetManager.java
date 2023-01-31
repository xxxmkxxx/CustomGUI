package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.HoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.hovere.ResetHoverEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Set;

@RequiredArgsConstructor
public class TargetManager {
    @Getter
    private AbstractNode currentTarget = AbstractNode.EMPTY_NODE;
    @Getter
    private AbstractNode lastTarget = AbstractNode.EMPTY_NODE;
    @Getter @Setter
    private AbstractNode activeNode = AbstractNode.EMPTY_NODE;

    private final EnumMap<WindowSection, Set<AbstractNode>> sections;

    public void update(int x, int y) {
        WindowSection windowSection = defineCursorSection(x, y);

        lastTarget = currentTarget;
        currentTarget = searchTargetNode(x, y, sections.get(windowSection));

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
        for (AbstractNode node : nodes) {
            if (node.getFrame().checkPosBelongs(x, y)) return node;
        }

        return AbstractNode.EMPTY_NODE;
    }

    private WindowSection defineCursorSection(int x, int y) {
        for (WindowSection windowSection : WindowSection.values()) {
            if (windowSection.getFrame().checkPosBelongs(x, y)) return windowSection;
        }

        return WindowSection.MIXED;
    }

    public WindowSection defineNodeSection(AbstractNode node) {
        for (WindowSection windowSection : WindowSection.values()) {
            if (windowSection.getFrame().isFrameBelong(node.getFrame())) return windowSection;
        }

        return WindowSection.MIXED;
    }
}
