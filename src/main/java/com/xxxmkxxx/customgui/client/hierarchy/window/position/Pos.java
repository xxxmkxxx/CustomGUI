package com.xxxmkxxx.customgui.client.hierarchy.window.position;

import com.xxxmkxxx.customgui.client.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
public class Pos implements Cloneable {
    private static final Pos DEFAULT_POS = Pos.builder().build(2, 1);
    private int x, y;
    private double xPercentValue, yPercentValue;
    private double xIndentPercent, yIndentPercent;

    protected Pos(int x, int y, double xPercentValue, double yPercentValue) {
        this.x = x;
        this.y = y;
        this.xPercentValue = xPercentValue;
        this.yPercentValue = yPercentValue;
        updateIndentPercents(xPercentValue, yPercentValue);
    }

    protected Pos(double xIndentPercent, double yIndentPercent, double xPercentValue, double yPercentValue) {
        this.xIndentPercent = xIndentPercent;
        this.yIndentPercent = yIndentPercent;
        this.xPercentValue = xPercentValue;
        this.yPercentValue = yPercentValue;
        this.x = Utils.nonNullIntValue(xIndentPercent * xPercentValue);
        this.y = Utils.nonNullIntValue(yIndentPercent * yPercentValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void updateIndentPercents(double xPercentValue, double yPercentValue) {
        xIndentPercent = x / xPercentValue;
        yIndentPercent = y / yPercentValue;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Pos(x, y, xPercentValue, yPercentValue, xIndentPercent, yIndentPercent);
    }

    public static Pos defaultPos() {
        try {
            return (Pos) DEFAULT_POS.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Pos moveByX(int distance) {
        x += distance;
        updateIndentPercents(xPercentValue, yPercentValue);
        return this;
    }

    public Pos moveByY(int distance) {
        y += distance;
        updateIndentPercents(xPercentValue, yPercentValue);
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
        updateIndentPercents(xPercentValue, yPercentValue);
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
        return "x - " + x + " y - " + y
                + " xPercentValue - " + xPercentValue + " yPercentValue - " + yPercentValue
                + " xIndentPercent - " + xIndentPercent + " yIndentPercent - " + yIndentPercent;
    }

    public static class Builder {
        private int x, y;
        private double xIndentPercent, yIndentPercent;
        private BiFunction<Double, Double, Pos> posFactory = (xPercentValue, yPercentValue) -> {
            return new Pos(10, 10, xPercentValue, yPercentValue);
        };

        public Builder() {
            x = y = 10;
            xIndentPercent = yIndentPercent = 10;
        }

        public Builder coords(int x, int y) {
            this.x = x;
            this.y = y;
            posFactory = (xPercentValue, yPercentValue) -> {
                return new Pos(this.x, this.y, xPercentValue, yPercentValue);
            };
            return this;
        }

        public Builder relativeCoords(double xIndentPercent, double yIndentPercent) {
            this.xIndentPercent = xIndentPercent;
            this.yIndentPercent = yIndentPercent;
            posFactory = (xPercentValue, yPercentValue) -> {
                return new Pos(this.xIndentPercent, this.yIndentPercent, xPercentValue, yPercentValue);
            };
            return this;
        }

        public Pos build(double xPercentValue, double yPercentValue) {
            return posFactory.apply(xPercentValue, yPercentValue);
        }
    }
}
