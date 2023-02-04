package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;

import java.util.*;

@Getter
public class WindowSectionNodes {
    private final EnumMap<WindowSection, Set<AbstractNode>> sections;
    private final List<AbstractNode> uncertainNodes;

    public WindowSectionNodes(Comparator<AbstractNode> comparator) {
        this.sections = new EnumMap<>(WindowSection.class);
        this.uncertainNodes = new ArrayList<>();
        Arrays.stream(WindowSection.values()).forEach(section -> sections.put(section, new TreeSet<>(comparator)));
    }

    public void addNode(AbstractNode node) {
        uncertainNodes.add(node);
    }

    public void addNode(AbstractNode node, WindowSection section) {
        sections.get(section).add(node);
    }

    public void divideNodesIntoSections() {
        uncertainNodes.forEach(this::divideNodeIntoSection);
    }

    public void divideNodeIntoSection(AbstractNode node) {
        node.initSection(node1 -> {
            WindowSection section = WindowSection.MIXED;

            for (WindowSection windowSection : WindowSection.values()) {
                if (windowSection.getFrame().isFrameBelong(node1.getFrame())) {
                    section = windowSection;
                    break;
                }
            }

           return section;
        });

        node.init(node1 -> addNode(node1, node1.getWindowSection()));
    }

    public Set<AbstractNode> getNodes(WindowSection section) {
        return sections.get(section);
    }

    public List<AbstractNode> getNodes() {
        List<AbstractNode> nodes = new ArrayList<>();

        sections.forEach((section, abstractNodes) -> {
            nodes.addAll(abstractNodes);
        });

        return nodes;
    }
}
