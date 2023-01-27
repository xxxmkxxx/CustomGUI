package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class AbstractFrame implements Frame {
    public static final AbstractFrame DEFAULT_FRAME = new AbstractFrame(10, 10, 10, 10) {};
    protected Pos initialStartPos;
    protected Pos initialStopPos;
    protected Pos startPos;
    protected Pos stopPos;
    protected int diagonal;
    protected int width;
    protected int height;

    protected AbstractFrame(int xPos, int yPos, int width, int height) {
        this.width = width;
        this.height = height;
        this.initialStartPos = new Pos(xPos, yPos);
        this.initialStopPos = new Pos(xPos + width, yPos + height);
        this.startPos = initialStartPos;
        this.stopPos = initialStopPos;
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
    }

    protected AbstractFrame(Pos startPos, int width, int height) {
        this(startPos.x(), startPos.y(), width, height);
    }

    protected AbstractFrame(Pos startPos, Pos stopPos) {
        this.width = stopPos.x() - startPos.x();
        this.height = stopPos.y() - startPos.y();
        this.initialStartPos = startPos;
        this.initialStopPos = stopPos;
        this.startPos = initialStartPos;
        this.stopPos = initialStopPos;
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
    }

    public void scaling(double widthScaleValue, double heightScaleValue) {
        System.out.println("initStart " + initialStartPos.x() + " " + initialStartPos.y());
        System.out.println("initStop " + initialStopPos.x() + " " + initialStopPos.y());

        startPos = new Pos(
                (int) (initialStartPos.x() * widthScaleValue),
                (int) (initialStartPos.y() * heightScaleValue)
        );

        stopPos = new Pos(
                (int) (initialStopPos.x() * widthScaleValue),
                (int) (initialStopPos.y() * heightScaleValue)
        );

        System.out.println("start " + startPos.x() + " " + startPos.y());
        System.out.println("stop " + stopPos.x() + " " + stopPos.y());
        System.out.println("======================");
    }

    public void setStartPos(Pos startPos) {
        Validator.checkNullObject(startPos);
        this.initialStartPos = startPos;
        updateFields();
        EventBus.CHANGE_FRAME_EVENT.callHandler(this);
    }

    public void setStartPos(int xStartPos, int yStartPos) {
        this.initialStartPos = new Pos(xStartPos, yStartPos);
        updateFields();
        EventBus.CHANGE_FRAME_EVENT.callHandler(this);
    }

    public void setStopPos(Pos stopPos) {
        Validator.checkNullObject(stopPos);
        this.initialStopPos = stopPos;
        updateFields();
        EventBus.CHANGE_FRAME_EVENT.callHandler(this);
    }

    public void setStopPos(int xStopPos, int yStopPos) {
        this.initialStopPos = new Pos(xStopPos, yStopPos);
        updateFields();
        EventBus.CHANGE_FRAME_EVENT.callHandler(this);
    }

    private void updateFields() {
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
