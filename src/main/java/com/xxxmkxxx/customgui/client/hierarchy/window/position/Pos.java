package com.xxxmkxxx.customgui.client.hierarchy.window.position;

import lombok.Getter;

@Getter
public class Pos implements Cloneable {
    private static final Pos DEFAULT_POS = Pos.builder().build(2, 1);
    private float x, y;
    private float xPercentValue, yPercentValue;
    private float xIndentPercent, yIndentPercent;

    protected Pos(float xIndentPercent, float yIndentPercent, float xPercentValue, float yPercentValue) {
        this.x = xIndentPercent * xPercentValue;
        this.y = yIndentPercent * yPercentValue;
        this.xIndentPercent = xIndentPercent;
        this.yIndentPercent = yIndentPercent;
        this.xPercentValue = xPercentValue;
        this.yPercentValue = yPercentValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    private void updateIndentPercents(float xPercentValue, float yPercentValue) {
        xIndentPercent = x / xPercentValue;
        yIndentPercent = y / yPercentValue;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Pos(xPercentValue, yPercentValue, xIndentPercent, yIndentPercent);
    }

    public static Pos defaultPos() {
        try {
            return (Pos) DEFAULT_POS.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Pos moveByX(float distance) {
        x += distance;
        updateIndentPercents(xPercentValue, yPercentValue);
        return this;
    }

    public Pos moveByY(float distance) {
        y += distance;
        updateIndentPercents(xPercentValue, yPercentValue);
        return this;
    }

    public static Pos moveByX(Pos pos, float distance) {
        pos.moveByX(distance);
        return pos;
    }

    public static Pos moveByY(Pos pos, float distance) {
        pos.moveByY(distance);
        return pos;
    }

    public Pos moveByXY(float xDistance, float yDistance) {
        x += xDistance;
        y += yDistance;
        updateIndentPercents(xPercentValue, yPercentValue);
        return this;
    }

    public static Pos moveByXY(Pos pos, float xDistance, float yDistance) {
        pos.moveByXY(xDistance, yDistance);
        return pos;
    }

    public float calculateSegmentLength(Pos secondPos) {
        return calculateSegmentLength(this, secondPos);
    }

    public static float calculateSegmentLength(Pos firstPos, Pos secondPos) {
        float differenceOnX = secondPos.getX() - firstPos.getX();
        float differenceOnY = secondPos.getY() - firstPos.getY();

        return (float) Math.sqrt(differenceOnX * differenceOnX + differenceOnY * differenceOnY);
    }

    @Override
    public String toString() {
        return "x - " + x + " y - " + y
                + " xPercentValue - " + xPercentValue + " yPercentValue - " + yPercentValue
                + " xIndentPercent - " + xIndentPercent + " yIndentPercent - " + yIndentPercent;
    }

    public static class Builder {
        private float x, y;
        private float xIndentPercent, yIndentPercent;

        public Builder() {
            x = y = 10;
        }

        public Builder coords(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder relativeCoords(float xIndentPercent, float yIndentPercent) {
            this.xIndentPercent = xIndentPercent;
            this.yIndentPercent = yIndentPercent;
            return this;
        }

        public Pos build(float xPercentValue, float yPercentValue) {
            xIndentPercent = xIndentPercent == 0.0 ? x / xPercentValue : xIndentPercent;
            yIndentPercent = yIndentPercent == 0.0 ? y / yPercentValue : yIndentPercent;

            return new Pos(xIndentPercent, yIndentPercent, xPercentValue, yPercentValue);
        }
    }
}
