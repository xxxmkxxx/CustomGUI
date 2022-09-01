package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.geometry.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.geometry.frame.StaticFrame;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

@Getter
public abstract class AbstractNode implements Node {
    protected AbstractFrame frame = new StaticFrame(0, 0, 18, 18, false);
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
        isTarget = frame.isPosBelongs(xPos, yPos);

        if (isTarget) {
            targetManager.setNode(this);
        }
    }

    public abstract void initRenderer(RendererType type);
}
