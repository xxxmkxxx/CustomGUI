package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.common.event.EventBus;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
public abstract class AbstractFrame implements Frame, Cloneable {
    private static final AbstractFrame DEFAULT_FRAME = new AbstractFrame(Pos.defaultPos(), 10, 10) {};
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

    protected AbstractFrame(int xPos, int yPos, int width, int height, double xPercentValue, double yPercentValue) {
        this.width = width;
        this.height = height;
        this.initialStartPos = Pos.builder().coords(xPos, yPos).build(xPercentValue, yPercentValue);
        this.initialStopPos = Pos.builder().coords(xPos + width, yPos + height).build(xPercentValue, yPercentValue);
        this.startPos = initialStartPos;
        this.stopPos = initialStopPos;
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
    }

    protected AbstractFrame(Pos startPos, double widthPercents, double heightPercents) {
        this(
                startPos,
                Pos.builder()
                        .relativeCoords(
                                startPos.getXIndentPercent() + widthPercents,
                                startPos.getYIndentPercent() + heightPercents
                        )
                        .build(startPos.getXPercentValue(), startPos.getYPercentValue())
        );
    }

    protected AbstractFrame(Pos startPos, Pos stopPos) {
        this.initialStartPos = startPos;
        this.initialStopPos = stopPos;
        this.startPos = initialStartPos;
        this.stopPos = initialStopPos;
        this.width = this.initialStopPos.getX() - this.initialStartPos.getX();
        this.height = this.initialStopPos.getY() - this.initialStartPos.getY();
        this.diagonal = Pos.calculateSegmentLength(startPos, stopPos);
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
        lastXPercentValue = xPercentValue;
        lastYPercentValue = yPercentValue;

        startPos = Pos.builder()
                .relativeCoords(startPos.getXIndentPercent(), startPos.getYIndentPercent())
                .build(lastXPercentValue, lastYPercentValue);

        stopPos = Pos.builder()
                .relativeCoords(stopPos.getXIndentPercent(), stopPos.getYIndentPercent())
                .build(lastXPercentValue, lastYPercentValue);
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
