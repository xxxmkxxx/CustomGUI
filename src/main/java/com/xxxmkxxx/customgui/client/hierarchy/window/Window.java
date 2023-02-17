package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.window.event.resize.ResizeWindowEventHandler;
import lombok.Getter;

@Getter
public class Window implements ResizeWindowEventHandler {
    private WindowSectionNodes windowSectionNodes;
    private int windowWidth;
    private int windowHeight;
    private int scaledWindowWidth;
    private int scaledWindowHeight;

    public Window(WindowSectionNodes windowSectionNodes, int windowWidth, int windowHeight, int scaledWindowWidth, int scaledWindowHeight) {
        this.windowSectionNodes = windowSectionNodes;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scaledWindowWidth = scaledWindowWidth;
        this.scaledWindowHeight = scaledWindowHeight;

        windowSectionNodes.divideNodesIntoSections();
    }

    public Window(int windowWidth, int windowHeight, int scaledWindowWidth, int scaledWindowHeight) {
        this(new WindowSectionNodes(new NodeFrameComparator()), windowWidth, windowHeight, scaledWindowWidth, scaledWindowHeight);
    }

    public void setWindowSectionNodes(WindowSectionNodes windowSectionNodes) {
        this.windowSectionNodes = windowSectionNodes;
        windowSectionNodes.divideNodesIntoSections();

        windowSectionNodes.getNodes().forEach(node -> {
            node.scaling(
                    (double) scaledWindowWidth / windowWidth,
                    (double) scaledWindowHeight / windowHeight
            );
        });
    }

    @Override
    public void onResize(int windowWidth, int windowHeight, int scaledWindowWidth, int scaledWindowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scaledWindowWidth = scaledWindowWidth;
        this.scaledWindowHeight = scaledWindowHeight;

        WindowSection.updateFrames(windowWidth, windowHeight);
        windowSectionNodes.getNodes().forEach(node -> {
            node.scaling(
                    (double) scaledWindowWidth / windowWidth,
                    (double) scaledWindowHeight / windowHeight
            );
        });
    }
}
