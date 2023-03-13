package com.xxxmkxxx.customgui.client.hierarchy.node.layout;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public class LayoutManager {
    public static void positionNodeRelativeTargetNode(AbstractNode target, AbstractNode node, Position position) {
        switch (position) {
            case TOP -> {
                int yStartCoordinate = target.getFrame().getStartPos().getY()
                        - target.getStyle().getMargins().getTop()
                        - node.getStyle().getMargins().getBottom()
                        - node.getFrame().getHeight();
                int yStopCoordinate = target.getFrame().getStartPos().getY()
                        - target.getStyle().getMargins().getTop()
                        - node.getStyle().getMargins().getBottom();

                Pos startPos = Pos.builder()
                        .coords(target.getFrame().getStartPos().getX(), yStartCoordinate)
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStartPos(startPos);

                Pos stopPos = Pos.builder()
                        .coords(node.getFrame().getStopPos().getX(), yStopCoordinate)
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStopPos(stopPos);
            }

            case BOTTOM -> {
                int yStartCoordinate = target.getFrame().getStopPos().getY()
                        + target.getStyle().getMargins().getBottom()
                        + node.getStyle().getMargins().getTop();
                int yStopCoordinate = target.getFrame().getStopPos().getY()
                        + target.getStyle().getMargins().getBottom()
                        + node.getStyle().getMargins().getTop()
                        + node.getFrame().getHeight();

                Pos startPos = Pos.builder()
                        .coords(target.getFrame().getStartPos().getX(), yStartCoordinate)
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStartPos(startPos);

                Pos stopPos = Pos.builder()
                        .coords(node.getFrame().getStopPos().getX(), yStopCoordinate)
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStopPos(stopPos);
            }

            case LEFT -> {
                int xStartCoordinate = target.getFrame().getStartPos().getX()
                        - target.getStyle().getMargins().getLeft()
                        - node.getStyle().getMargins().getRight()
                        - node.getFrame().getWidth();
                int xStopCoordinate = target.getFrame().getStartPos().getX()
                        - target.getStyle().getMargins().getLeft()
                        - node.getStyle().getMargins().getRight();

                Pos startPos = Pos.builder()
                        .coords(xStartCoordinate, target.getFrame().getStartPos().getY())
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStartPos(startPos);

                Pos stopPos = Pos.builder()
                        .coords(xStopCoordinate, node.getFrame().getStopPos().getY())
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStopPos(stopPos);
            }

            case RIGHT -> {
                int xStartCoordinate = target.getFrame().getStopPos().getX()
                        + target.getStyle().getMargins().getRight()
                        + node.getStyle().getMargins().getLeft();
                int xStopCoordinate = target.getFrame().getStopPos().getX()
                        + target.getStyle().getMargins().getRight()
                        + node.getStyle().getMargins().getLeft()
                        + node.getFrame().getWidth();

                Pos startPos = Pos.builder()
                        .coords(xStartCoordinate, target.getFrame().getStartPos().getY())
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStartPos(startPos);

                Pos stopPos = Pos.builder()
                        .coords(xStopCoordinate, node.getFrame().getStopPos().getY())
                        .build(target.getFrame().getLastXPercentValue(), target.getFrame().getLastYPercentValue());

                node.getFrame().moveStopPos(stopPos);
            }
        }
    }
}
