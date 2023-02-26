package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public abstract class AbstractFrame implements Frame, Cloneable {
    private static final AbstractFrame DEFAULT_FRAME;
    protected Pos initialStartPos;
    protected Pos initialStopPos;
    @Setter
    protected Pos startPos;
    @Setter
    protected Pos stopPos;
    protected int diagonal;
    protected int width;
    protected int height;
    protected double lastXPercentValue;
    protected double lastYPercentValue;

    static {
        Pos startPos = Pos.defaultPos();
        Pos stopPos = Pos.builder()
                .relativeCoords(
                        startPos.getXIndentPercent() + 1,
                        startPos.getYIndentPercent() + 2
                )
                .build(startPos.getXPercentValue(), startPos.getYPercentValue());
        DEFAULT_FRAME = new AbstractFrame(startPos, stopPos) {};
    }

    protected AbstractFrame(Pos startPos, Pos stopPos) {
        this.initialStartPos = startPos;
        this.initialStopPos = stopPos;
        try {
            this.startPos = (Pos) startPos.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        try {
            this.stopPos = (Pos) stopPos.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        this.width = this.initialStopPos.getX() - this.initialStartPos.getX();
        this.height = this.initialStopPos.getY() - this.initialStartPos.getY();
        this.diagonal = Pos.calculateSegmentLength(this.startPos, this.stopPos);
        this.lastXPercentValue = startPos.getXPercentValue();
        this.lastYPercentValue = startPos.getYPercentValue();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        SimpleFrame frame = new SimpleFrame((Pos) initialStartPos.clone(), (Pos) initialStopPos.clone());
        frame.setStartPos((Pos) startPos.clone());
        frame.setStopPos((Pos) stopPos.clone());
        return frame;
    }

    public static AbstractFrame defaultFrame() {
        try {
            return (AbstractFrame) DEFAULT_FRAME.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void scaling(double xPercentValue, double yPercentValue) {
        startPos = Pos.builder()
                .relativeCoords(startPos.getXIndentPercent(), startPos.getYIndentPercent())
                .build(xPercentValue, yPercentValue);

        stopPos = Pos.builder()
                .relativeCoords(stopPos.getXIndentPercent(), stopPos.getYIndentPercent())
                .build(xPercentValue, yPercentValue);

        lastXPercentValue = xPercentValue;
        lastYPercentValue = yPercentValue;
    }

    public void moveStartPos(int xDistance, int yDistance) {
        this.initialStartPos.moveByXY(xDistance, yDistance);
        this.startPos = Pos.builder()
                .relativeCoords(initialStartPos.getXIndentPercent(), initialStartPos.getYIndentPercent())
                .build(lastXPercentValue, lastYPercentValue);

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
        this.stopPos = Pos.builder()
                .relativeCoords(initialStopPos.getXIndentPercent(), initialStopPos.getYIndentPercent())
                .build(lastXPercentValue, lastYPercentValue);

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
    public String toString() {
        return startPos.toString() + "\n" + stopPos.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPos, stopPos, width, height);
    }
}
