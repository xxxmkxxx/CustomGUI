package com.xxxmkxxx.customgui.client.geometry.position;

public record Pos(int x, int y) {
    public int calculateSegmentLength(Pos secondPos) {
        return calculateSegmentLength(this, secondPos);
    }

    public static int calculateSegmentLength(Pos firstPos, Pos secondPos) {
        int differenceOnX = secondPos.x() - firstPos.x();
        int differenceOnY = secondPos.y() - firstPos.y();

        return Math.abs(differenceOnX * differenceOnX + differenceOnY * differenceOnY);
    }
}
