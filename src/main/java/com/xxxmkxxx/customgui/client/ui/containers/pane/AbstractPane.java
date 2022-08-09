package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.common.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.Node;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public abstract class AbstractPane implements Pane {
    protected List<Node> nodes;
    protected PaneState state;
    protected MatrixStack matrixStack;
    protected Frame frame;
}