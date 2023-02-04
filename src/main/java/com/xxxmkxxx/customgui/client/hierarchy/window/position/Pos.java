package com.xxxmkxxx.customgui.client.hierarchy.window.position;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pos {
    public static Pos DEFAULT_POS = new Pos(10, 10);
    private int x;
    private int y;

    public Pos moveByX(int distance) {
        x += distance;
        return this;
    }

    public Pos moveByY(int distance) {
        y += distance;
        return this;
    }

    public static Pos moveByX(Pos pos, int distance) {
        pos.moveByX(distance);
        return pos;
    }

    public static Pos moveByY(Pos pos, int distance) {
        pos.moveByY(distance);
        return pos;
    }

    public Pos moveByXY(int xDistance, int yDistance) {
        x += xDistance;
        y += yDistance;
        return this;
    }

    public static Pos moveByXY(Pos pos, int xDistance, int yDistance) {
        pos.moveByXY(xDistance, yDistance);
        return pos;
    }

    public int calculateSegmentLength(Pos secondPos) {
        return calculateSegmentLength(this, secondPos);
    }

    public static int calculateSegmentLength(Pos firstPos, Pos secondPos) {
        int differenceOnX = secondPos.getX() - firstPos.getX();
        int differenceOnY = secondPos.getY() - firstPos.getY();

        return (int) Math.sqrt(differenceOnX * differenceOnX + differenceOnY * differenceOnY);
    }

    @Override
    public String toString() {
        return "x - " + x + " y - " + y;
    }
}
