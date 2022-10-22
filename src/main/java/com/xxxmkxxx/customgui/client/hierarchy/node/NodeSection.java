package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.geometry.position.Pos;
import lombok.Getter;

import java.util.function.Consumer;

import static com.xxxmkxxx.customgui.client.common.MinecraftOptions.windowHeight;
import static com.xxxmkxxx.customgui.client.common.MinecraftOptions.windowWidth;

@Getter
public enum NodeSection {
    TOP(
            new Pos(0, 0),
            new Pos(windowWidth, windowHeight / 4)
    ),

    BOTTOM(
            new Pos(0, (windowHeight / 4) * 3),
            new Pos(windowWidth, windowHeight)
    ),

    LEFT(
            new Pos(0, windowHeight / 4),
            new Pos(windowWidth / 3, (windowHeight / 4) * 3)
    ),

    RIGHT(
            new Pos((windowWidth / 3) * 2, windowHeight / 4),
            new Pos(windowWidth, (windowHeight / 4) * 3)
    ),

    CENTER(
            new Pos(windowWidth / 3, windowHeight / 4),
            new Pos((windowWidth / 3) * 2, (windowHeight / 4) * 3)
    ),

    MIXED(
            new Pos(0, 0),
            new Pos(windowWidth, windowHeight)
    );

    private AbstractFrame frame;

    NodeSection(Pos startPos, Pos stopPos) {
        frame = new StaticFrame(startPos, stopPos, false);
    }

    public static void forEach(Consumer<NodeSection> consumer) {
        for (NodeSection section : values()) {
            consumer.accept(section);
        }
    }
}
