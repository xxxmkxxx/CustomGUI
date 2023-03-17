package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.layout.Position;
import com.xxxmkxxx.customgui.client.hierarchy.window.event.resize.ResizeWindowEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public class Window implements ResizeWindowEventHandler {
    private WindowSectionNodes windowSectionNodes;
    private int windowWidth;
    private int windowHeight;
    private int lastWindowWidth;
    private int lastWindowHeight;
    private float xPercentValue;
    private float yPercentValue;
    private float lastXPercentValue;
    private float lastYPercentValue;

    public Window(WindowSectionNodes windowSectionNodes, int windowWidth, int windowHeight) {
        this.windowSectionNodes = windowSectionNodes;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.lastWindowWidth = windowWidth;
        this.lastWindowHeight = windowHeight;

        updatePercentValues();
        windowSectionNodes.divideNodesIntoSections();
    }

    public Window(int windowWidth, int windowHeight) {
        this(new WindowSectionNodes(new NodeFrameComparator()), windowWidth, windowHeight);
    }

    private void updatePercentValues() {
        lastXPercentValue = xPercentValue;
        lastYPercentValue = yPercentValue;
        xPercentValue = windowWidth / 100f;
        yPercentValue = windowHeight / 100f;
    }


    public void setWindowSectionNodes(WindowSectionNodes windowSectionNodes) {
        this.windowSectionNodes = windowSectionNodes;
        windowSectionNodes.divideNodesIntoSections();

        windowSectionNodes.getNodes().forEach(node -> {
            node.scaling(this);
        });
    }

    @Override
    public void onResize(int windowWidth, int windowHeight) {
        lastWindowWidth = this.windowWidth;
        lastWindowHeight = this.windowHeight;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        updatePercentValues();

        WindowSection.updateFrames(windowWidth, windowHeight);
        windowSectionNodes.getNodes().forEach(node -> {
            node.scaling(this);
        });
    }
}
