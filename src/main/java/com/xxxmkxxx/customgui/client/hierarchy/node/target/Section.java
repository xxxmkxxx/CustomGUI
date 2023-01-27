package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@SuppressWarnings("all")
public enum Section {
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
    private AbstractFrame frame;
    private final BiFunction<Integer, Integer, Pos> expressionStartPos;
    private final BiFunction<Integer, Integer, Pos> expressionStopPos;

    public void initFrame(int windowWidth, int windowHeight) {
        Pos pos1 = expressionStartPos.apply(windowWidth, windowHeight);
        Pos pos2 = expressionStopPos.apply(windowWidth, windowHeight);

        frame = new SimpleFrame(
                expressionStartPos.apply(windowWidth, windowHeight),
                expressionStopPos.apply(windowWidth, windowHeight)
        );
    }

    public static void updateFrames(int windowWidth, int windowHeight) {
        for (Section section : values()) {
            section.initFrame(windowWidth, windowHeight);
        }
    }
}
