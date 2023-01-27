package com.xxxmkxxx.customgui.client.hierarchy.window;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.event.resize.ResizeWindowEventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Window implements ResizeWindowEventHandler {
    private List<AbstractNode> nodes = new ArrayList<>();
    private int windowWidth = 1;
    private int windowHeight = 1;
    private int scaledWindowWidth = 1;
    private int scaledWindowHeight = 1;

    @Override
    public void onResize(int windowWidth, int windowHeight, int scaledWindowWidth, int scaledWindowHeight) {
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEEEESIIIIIIIIIIIIIIIIIIIIIIIIZEEEEEEEEEEEEEEEEEEEEEE");
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scaledWindowWidth = scaledWindowWidth;
        this.scaledWindowHeight = scaledWindowHeight;

        nodes.forEach(node -> node.scaling((double) scaledWindowWidth / windowWidth, (double) scaledWindowHeight / windowHeight));
    }

    public void setNodes(List<AbstractNode> nodes) {
        this.nodes = nodes;
        onResize(windowWidth, windowHeight, scaledWindowWidth, scaledWindowHeight);
    }
}
