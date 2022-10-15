package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.ui.controls.button.AbstractButton;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

@Getter
public abstract class AbstractNode implements Node {
    public static final AbstractNode EMPTY_NODE = new AbstractNode() {
        @Override
        public void initRenderer(RendererType type) {

        }
    };

    protected AbstractFrame frame = new StaticFrame(0, 0, 18, 18, false);
    @Setter
    protected Style style = new Style();
    @Setter
    protected MatrixStack matrixStack = new MatrixStack();
    protected NodeState<AbstractNode> state = States.DISPLAYED;
    @SuppressWarnings("rawtypes")
    protected NodeRenderer renderer;
    @Getter
    protected boolean isTarget;

    @Override
    public void hide() {
        state = States.HIDED;
    }

    @Override
    public void display() {
        state = States.HIDED;
    }

    public void updateTarget(int xPos, int yPos, TargetManager targetManager) {
        if (state != States.DISPLAYED) return;

        isTarget = frame.checkPosBelongs(xPos, yPos);

        if (isTarget) {
            targetManager.setNode(this);
        }
    }

    public abstract void initRenderer(RendererType type);
}
