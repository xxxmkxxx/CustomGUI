package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@SuppressWarnings("all")
public enum WindowSection {
    TOP(
            (width, height) -> new Pos(0, 0),
            (width, height) -> new Pos(width, height / 5)
    ),

    BOTTOM(
            (width, height) -> new Pos(0, (height / 5) * 4),
            (width, height) -> new Pos(width, height)
    ),

    LEFT(
            (width, height) -> new Pos(0, height / 5),
            (width, height) -> new Pos(width / 8, (height / 5) * 4)
    ),

    RIGHT(
            (width, height) -> new Pos((width / 8) * 7, height / 5),
            (width, height) -> new Pos(width, (height / 5) * 4)
    ),

    CENTER(
            (width, height) -> new Pos(width / 8, height / 5),
            (width, height) -> new Pos((width / 8) * 7, (height / 5) * 4)
    ),

    MIXED(
            (width, height) -> new Pos(0, 0),
            (width, height) -> new Pos(width, height)
    );

    @Getter
    private AbstractFrame frame = AbstractFrame.DEFAULT_FRAME;
    @Getter
    private final BiFunction<Integer, Integer, Pos> expressionStartPos;
    @Getter
    private final BiFunction<Integer, Integer, Pos> expressionStopPos;

    public void initFrame(Pos startPos, Pos stopPos) {
        this.frame = new SimpleFrame(startPos, stopPos);
    }

    public static void initFrames(int windowWidth, int windowHeight) {
        for (WindowSection windowSection : values()) {
            windowSection.initFrame(
                    windowSection.getExpressionStartPos().apply(windowWidth, windowHeight),
                    windowSection.getExpressionStopPos().apply(windowWidth, windowHeight)
            );
        }
    }

    public static void updateFrames(double widthScaleValue, double heightScaleValue) {
        for (WindowSection windowSection : values()) {
            windowSection.getFrame().scaling(widthScaleValue, heightScaleValue);
        }
    }
}
