package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("all")
public enum WindowSection {
    TOP(
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(0, 0)
                        .build(xPercentValue, yPercentValue);
            },
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(width, height / 5)
                        .build(xPercentValue, yPercentValue);
            }
    ),

    BOTTOM(
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(0, (height / 5) * 4)
                        .build(xPercentValue, yPercentValue);
            },
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(width, height)
                        .build(xPercentValue, yPercentValue);
            }
    ),

    LEFT(
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(0, height / 5)
                        .build(xPercentValue, yPercentValue);
            },
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(width / 8, (height / 5) * 4)
                        .build(xPercentValue, yPercentValue);
            }
    ),

    RIGHT(
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords((width / 8) * 7, height / 5)
                        .build(xPercentValue, yPercentValue);
            },
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(width, (height / 5) * 4)
                        .build(xPercentValue, yPercentValue);
            }
    ),

    CENTER(
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(width / 8, height / 5)
                        .build(xPercentValue, yPercentValue);
            },
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords((width / 8) * 7, (height / 5) * 4)
                        .build(xPercentValue, yPercentValue);
            }
    ),

    MIXED(
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(0, 0)
                        .build(xPercentValue, yPercentValue);
            },
            (width, height, xPercentValue, yPercentValue) -> {
                return Pos.builder()
                        .coords(width, height)
                        .build(xPercentValue, yPercentValue);
            }
    );

    @Getter
    private AbstractFrame frame = AbstractFrame.defaultFrame();
    @Getter
    private final WindowSectionPosInitializer expressionStartPos;
    @Getter
    private final WindowSectionPosInitializer expressionStopPos;

    public void initFrame(Pos startPos, Pos stopPos) {
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
    }

    public static void initFrames(int windowWidth, int windowHeight) {
        float xPercentValue = windowWidth / 100f;
        float yPercentValue = windowHeight / 100f;

        for (WindowSection windowSection : values()) {
            windowSection.initFrame(
                    windowSection.getExpressionStartPos().init(windowWidth, windowHeight, xPercentValue, yPercentValue),
                    windowSection.getExpressionStopPos().init(windowWidth, windowHeight, xPercentValue, yPercentValue)
            );
        }
    }

    public static void updateFrames(int windowWidth, int windowHeight) {
        float xPercentValue = windowWidth / 100f;
        float yPercentValue = windowHeight / 100f;

        for (WindowSection windowSection : values()) {
            windowSection.getFrame().scaling(xPercentValue, yPercentValue);
        }
    }

    private interface WindowSectionPosInitializer {
        Pos init(int windowWidth, int windowHeight, float xPercentValue, float yPercentValue);
    }
}
