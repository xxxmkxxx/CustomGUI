package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.geometry.Frame;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

@Getter
public abstract class AbstractPane extends AbstractNode implements Pane {
    protected List<AbstractNode> nodes;
    protected MatrixStack matrixStack;
    protected Frame frame;
}