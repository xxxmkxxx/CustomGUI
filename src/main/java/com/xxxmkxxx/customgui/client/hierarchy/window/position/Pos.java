package com.xxxmkxxx.customgui.client.hierarchy.window.position;

public record Pos(int x, int y) {
    public static Pos DEFAULT_POS = new Pos(10, 10);
    public int calculateSegmentLength(Pos secondPos) {
        return calculateSegmentLength(this, secondPos);
    }

    public static int calculateSegmentLength(Pos firstPos, Pos secondPos) {
        int differenceOnX = secondPos.x() - firstPos.x();
        int differenceOnY = secondPos.y() - firstPos.y();

        return Math.abs(differenceOnX * differenceOnX + differenceOnY * differenceOnY);
    }

    @Override
    public String toString() {
        return "x - " + x + " y - " + y;
    }
}
