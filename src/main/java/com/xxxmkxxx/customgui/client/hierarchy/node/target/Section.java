package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@SuppressWarnings("all")
public enum Section {
    TOP(
            (width, height) -> new Pos(0, 0),
            (width, height) -> new Pos(width, height / 4)
    ),

    BOTTOM(
            (width, height) -> new Pos(0, (height / 4) * 3),
            (width, height) -> new Pos(width, height)
    ),

    LEFT(
            (width, height) -> new Pos(0, height / 4),
            (width, height) -> new Pos(width / 3, (height / 4) * 3)
    ),

    RIGHT(
            (width, height) -> new Pos((width / 3) * 2, height / 4),
            (width, height) -> new Pos(width, (height / 4) * 3)
    ),

    CENTER(
            (width, height) -> new Pos(width / 3, height / 4),
            (width, height) -> new Pos((width / 3) * 2, (height / 4) * 3)
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

        frame = new StaticFrame(
                expressionStartPos.apply(windowWidth, windowHeight),
                expressionStopPos.apply(windowWidth, windowHeight),
                false
        );

        System.out.println(this + " start-" + pos1 + " stop-" + pos2);
    }

    public static void updateFrames(int windowWidth, int windowHeight) {
        for (Section section : values()) {
            section.initFrame(windowWidth, windowHeight);
        }
    }
}
