package com.xxxmkxxx.customgui.client.geometry.frame;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class AbstractFrame implements Frame {
    protected Pos startPos;
    protected Pos stopPos;
    protected int diagonal;
    protected int width;
    protected int height;

    protected AbstractFrame(int xPos, int yPos, int width, int height) {
        this.width = width;
        this.height = height;
        this.startPos = new Pos(xPos, yPos);
        this.stopPos = new Pos(xPos + width, yPos + height);
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
    }

    protected AbstractFrame(Pos startPos, int width, int height) {
        this(startPos.x(), startPos.y(), width, height);
    }

    protected AbstractFrame(Pos startPos, Pos stopPos) {
        this.startPos = startPos;
        this.stopPos = stopPos;
        this.width = stopPos.x() - startPos.x();
        this.height = stopPos.y() - startPos.y();
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
    }

    @Override
    public boolean checkPosBelongs(int xPos, int yPos) {
        return checkPosBelongs(startPos, stopPos, xPos, yPos);
    }

    @Override
    public boolean checkPosBelongs(Pos pos) {
        return checkPosBelongs(pos.x(), pos.y());
    }

    public static boolean checkPosBelongs(Pos startPos, Pos stopPos, int xPos, int yPos) {
        boolean xBelongs = false, yBelongs = false;

        if (startPos.x() < stopPos.x()) {
            xBelongs = xPos >= startPos.x() && xPos <= stopPos.x();
        } else if (startPos.x() > stopPos.x()) {
            xBelongs = xPos <= startPos.x() && xPos >= stopPos.x();
        }

        if (startPos.y() < stopPos.y()) {
            yBelongs = yPos >= startPos.y() && yPos <= stopPos.y();
        } else if (startPos.y() > stopPos.y()) {
            yBelongs = yPos <= startPos.y() && yPos >= stopPos.y();
        }

        return xBelongs && yBelongs;
    }

    public boolean isFrameBelong(AbstractFrame frame) {
        return checkPosBelongs(frame.getStartPos()) && checkPosBelongs(frame.getStopPos());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFrame)) return false;
        AbstractFrame that = (AbstractFrame) o;
        return width == that.width && height == that.height && startPos.equals(that.startPos) && stopPos.equals(that.stopPos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPos, stopPos, width, height);
    }
}
