package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.common.comparators.NodeFrameComparator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
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
    private double xPercentValue;
    private double yPercentValue;
    private double lastXPercentValue;
    private double lastYPercentValue;

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
        xPercentValue = windowWidth / 100d;
        yPercentValue = windowHeight / 100d;
    }


    public void setWindowSectionNodes(WindowSectionNodes windowSectionNodes) {
        this.windowSectionNodes = windowSectionNodes;
        windowSectionNodes.divideNodesIntoSections();

        windowSectionNodes.getNodes().forEach(node -> {
            node.scaling(this);
        });
    }

    public void arrangeNodeRelativelyTargetNode(AbstractNode targetNode, AbstractNode node, AbstractNode.Position position) {
        switch (position) {
            case RIGHT -> {
                Pos startPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStopPos().getX(),
                                targetNode.getFrame().getInitialStartPos().getY()
                        )
                        .build(xPercentValue, yPercentValue);

                Pos stopPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStopPos().getX() + node.getFrame().getWidth(),
                                targetNode.getFrame().getInitialStartPos().getY() + node.getFrame().getHeight()
                        )
                        .build(xPercentValue, yPercentValue);

                node.getFrame().setStartPos(startPos);
                node.getFrame().setStopPos(stopPos);
            }
            case LEFT -> {
                Pos startPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStartPos().getX() - node.getFrame().getWidth(),
                                targetNode.getFrame().getInitialStartPos().getY()
                        )
                        .build(xPercentValue, yPercentValue);

                Pos stopPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStartPos().getX(),
                                targetNode.getFrame().getInitialStartPos().getY() + node.getFrame().getHeight()
                        )
                        .build(xPercentValue, yPercentValue);

                node.getFrame().setStartPos(startPos);
                node.getFrame().setStopPos(stopPos);
            }
            case BOTTOM -> {
                Pos startPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStartPos().getX(),
                                targetNode.getFrame().getInitialStopPos().getY()
                        )
                        .build(xPercentValue, yPercentValue);

                Pos stopPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStartPos().getX() + node.getFrame().getWidth(),
                                targetNode.getFrame().getInitialStopPos().getY() + node.getFrame().getHeight()
                        )
                        .build(xPercentValue, yPercentValue);

                node.getFrame().setStartPos(startPos);
                node.getFrame().setStopPos(stopPos);
            }
            case TOP -> {
                Pos startPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStartPos().getX(),
                                targetNode.getFrame().getInitialStartPos().getY() - node.getFrame().getHeight()
                        )
                        .build(xPercentValue, yPercentValue);

                Pos stopPos = Pos.builder()
                        .coords(
                                targetNode.getFrame().getInitialStartPos().getX() + node.getFrame().getWidth(),
                                targetNode.getFrame().getInitialStartPos().getY()
                        )
                        .build(xPercentValue, yPercentValue);

                node.getFrame().setStartPos(startPos);
                node.getFrame().setStopPos(stopPos);
            }
        }
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
