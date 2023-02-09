package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public abstract class AbstractFrame implements Frame, Cloneable {
    public static final AbstractFrame DEFAULT_FRAME = new AbstractFrame(10, 10, 10, 10) {};
    protected Pos initialStartPos;
    protected Pos initialStopPos;
    @Setter
    protected Pos startPos;
    @Setter
    protected Pos stopPos;
    protected int diagonal;
    protected int width;
    protected int height;
    protected double lastWidthScaleValue;
    protected double lastHeightScaleValue;

    protected AbstractFrame(int xPos, int yPos, int width, int height) {
        this.width = width;
        this.height = height;
        this.initialStartPos = new Pos(xPos, yPos);
        this.initialStopPos = new Pos(xPos + width, yPos + height);
        this.startPos = initialStartPos;
        this.stopPos = initialStopPos;
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
        this.lastWidthScaleValue = 1;
        this.lastHeightScaleValue = 1;
    }

    protected AbstractFrame(Pos startPos, int width, int height) {
        this(startPos.getX(), startPos.getY(), width, height);
    }

    protected AbstractFrame(Pos startPos, Pos stopPos) {
        this.width = stopPos.getX() - startPos.getX();
        this.height = stopPos.getY() - startPos.getY();
        this.initialStartPos = startPos;
        this.initialStopPos = stopPos;
        this.startPos = initialStartPos;
        this.stopPos = initialStopPos;
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
        this.lastWidthScaleValue = 1;
        this.lastHeightScaleValue = 1;
    }

    public void scaling(double widthScaleValue, double heightScaleValue) {
        lastWidthScaleValue = widthScaleValue;
        lastHeightScaleValue = heightScaleValue;

        startPos = new Pos(
                Utils.nonNullIntValue(initialStartPos.getX() * widthScaleValue),
                Utils.nonNullIntValue(initialStartPos.getY() * heightScaleValue)
        );

        stopPos = new Pos(
                Utils.nonNullIntValue(initialStopPos.getX() * widthScaleValue),
                Utils.nonNullIntValue(initialStopPos.getY() * heightScaleValue)
        );
    }

    public void moveStartPos(int xDistance, int yDistance) {
        this.initialStartPos.moveByXY(xDistance, yDistance);
        this.startPos = new Pos(
                Utils.nonNullIntValue(initialStartPos.getX() * lastWidthScaleValue),
                Utils.nonNullIntValue(initialStartPos.getY() * lastHeightScaleValue)
        );

        updateFields(startPos, stopPos);
        EventBus.CHANGE_FRAME_EVENT.callHandler(this);
    }

    public void moveStartPos(Pos startPos) {
        moveStartPos(
                startPos.getX() - this.startPos.getX(),
                startPos.getY() - this.startPos.getY()
        );
    }

    public void moveStopPos(int xDistance, int yDistance) {
        this.initialStopPos.moveByXY(xDistance, yDistance);
        this.stopPos = new Pos(
                Utils.nonNullIntValue(initialStopPos.getX() * lastWidthScaleValue),
                Utils.nonNullIntValue(initialStopPos.getY() * lastHeightScaleValue)
        );

        updateFields(startPos, stopPos);
        EventBus.CHANGE_FRAME_EVENT.callHandler(this);
    }

    public void moveStopPos(Pos stopPos) {
        moveStopPos(stopPos.getX(), stopPos.getY());
    }

    private void updateFields(Pos startPos, Pos stopPos) {
        this.width = stopPos.getX() - startPos.getX();
        this.height = stopPos.getY() - startPos.getY();
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
    }

    @Override
    public boolean checkPosBelongs(int xPos, int yPos) {
        return checkPosBelongs(startPos, stopPos, xPos, yPos);
    }

    @Override
    public boolean checkPosBelongs(Pos pos) {
        return checkPosBelongs(pos.getX(), pos.getY());
    }

    public static boolean checkPosBelongs(Pos startPos, Pos stopPos, int xPos, int yPos) {
        boolean xBelongs = false, yBelongs = false;

        if (startPos.getX() < stopPos.getX()) {
            xBelongs = xPos >= startPos.getX() && xPos <= stopPos.getX();
        } else if (startPos.getX() > stopPos.getX()) {
            xBelongs = xPos <= startPos.getX() && xPos >= stopPos.getX();
        }

        if (startPos.getY() < stopPos.getY()) {
            yBelongs = yPos >= startPos.getY() && yPos <= stopPos.getY();
        } else if (startPos.getY() > stopPos.getY()) {
            yBelongs = yPos <= startPos.getY() && yPos >= stopPos.getY();
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
