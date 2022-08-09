package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.common.Builder;
import com.xxxmkxxx.customgui.client.common.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.Node;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class SimplePane extends AbstractPane {
    private int color;

    @Override
    public boolean hide() {

        return true;
    }

    @Override
    public boolean view() {
        state.execute(
                matrixStack,
                frame.getStartPos().x(), frame.getStartPos().y(),
                frame.getStopPos().x(), frame.getStopPos().y(),
                color
        );

        return true;
    }

    public static SimplePaneBuilder builder() {
        return new SimplePaneBuilder();
    }

    public static class SimplePaneBuilder implements Builder<SimplePane> {
        private SimplePane simplePane = new SimplePane();

        public SimplePaneBuilder color(int hexColorCode) {
            simplePane.color = hexColorCode;

            return this;
        }

        public SimplePaneBuilder state(PaneState state) {
            simplePane.state = state;

            return this;
        }

        public SimplePaneBuilder frame(Frame frame) {
            simplePane.frame = frame;

            return this;
        }

        public SimplePaneBuilder matrixStack(MatrixStack matrixStack) {
            simplePane.matrixStack = matrixStack;

            return this;
        }

        public SimplePaneBuilder nodes(List<Node> nodes) {
            simplePane.nodes = nodes;

            return this;
        }

        @Override
        public SimplePane build() {
            return simplePane;
        }
    }
}