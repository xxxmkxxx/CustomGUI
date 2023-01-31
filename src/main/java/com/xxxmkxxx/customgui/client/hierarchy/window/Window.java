package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.hierarchy.window.event.resize.ResizeWindowEventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Window implements ResizeWindowEventHandler {
    private WindowSectionNodes windowSectionNodes = new WindowSectionNodes();
    private int windowWidth = 1;
    private int windowHeight = 1;
    private int scaledWindowWidth = 1;
    private int scaledWindowHeight = 1;

    @Override
    public void onResize(int windowWidth, int windowHeight, int scaledWindowWidth, int scaledWindowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scaledWindowWidth = scaledWindowWidth;
        this.scaledWindowHeight = scaledWindowHeight;

        WindowSection.updateFrames(windowWidth, windowHeight);
        windowSectionNodes.getNodes().forEach(node -> {
            node.scaling(
                    (double) scaledWindowWidth / windowWidth,
                    (double) scaledWindowHeight / windowHeight
            );
        });
    }
}
