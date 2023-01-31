package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class WindowSectionNodes {
    private final EnumMap<WindowSection, Set<AbstractNode>> sections = new EnumMap<>(WindowSection.class);

    public void init(Comparator<AbstractNode> comparator) {
        Arrays.stream(WindowSection.values()).forEach(section -> sections.put(section, new TreeSet<>(comparator)));
    }

    public void addNode(AbstractNode node, WindowSection section) {
        sections.get(section).add(node);
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
