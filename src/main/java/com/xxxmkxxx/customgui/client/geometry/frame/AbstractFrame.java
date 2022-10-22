package com.xxxmkxxx.customgui.client.geometry.frame;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import lombok.Getter;

@Getter
public abstract class AbstractFrame implements Frame {
    protected Pos startPos;
    protected Pos stopPos;
    protected int width;
    protected int height;
    protected boolean startInCenter;

    protected AbstractFrame(int xPos, int yPos, int width, int height, boolean startInCenter) {
        this.width = width;
        this.height = height;
        this.startInCenter = startInCenter;

        if (startInCenter) {
            this.startPos = new Pos(xPos - width / 2, yPos - height / 2);
            this.stopPos = new Pos(xPos + width / 2, yPos + height / 2);
        } else {
            this.startPos = new Pos(xPos, yPos);
            this.stopPos = new Pos(xPos + width, yPos + height);
        }
    }

    protected AbstractFrame(Pos startPos, int width, int height, boolean startInCenter) {
        this(startPos.x(), startPos.y(), width, height, startInCenter);
    }

    protected AbstractFrame(Pos startPos, Pos stopPos, boolean startInCenter) {
        this.startPos = startPos;
        this.stopPos = stopPos;
        this.startInCenter = startInCenter;
        this.width = stopPos.x() - startPos.x();
        this.height = stopPos.y() - startPos.y();
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
}
