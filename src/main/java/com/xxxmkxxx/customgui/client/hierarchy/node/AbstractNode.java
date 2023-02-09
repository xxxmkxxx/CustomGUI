package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public abstract class AbstractNode implements Node {
    public static final AbstractNode EMPTY_NODE = new AbstractNode() {
        @Override
        public void initRenderer(RendererType type) {

        }
    };
    private static int currentIdNumber = 0;
    private final int id;

    protected AbstractFrame frame = AbstractFrame.DEFAULT_FRAME;
    @Getter
    protected WindowSection windowSection = WindowSection.MIXED;
    @Setter
    protected Style style = Style.defaultStyle();
    @Setter
    protected MatrixStack matrixStack = new MatrixStack();
    protected NodeState<AbstractNode> state = States.DISPLAYED;
    @SuppressWarnings("rawtypes")
    protected NodeRenderer renderer;
    protected RendererType rendererType;
    protected AbstractNode() {
        id = generateId();
    }
    private static int generateId() {
        return ++currentIdNumber;
    }

    @Override
    public void hide() {
        state = States.HIDED;
    }

    @Override
    public void display() {
        state = States.DISPLAYED;
    }

    public void initRenderer(RendererType type) {
        this.rendererType = type;
    }

    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        this.windowSection = initMethod.apply(this);
    }

    public void init(Consumer<AbstractNode> initMethod) {
        initMethod.accept(this);
    }

    public void scaling(double widthScaleValue, double heightScaleValue) {
        frame.scaling(widthScaleValue, heightScaleValue);
    }

    public AbstractNode arrangeRelatively(AbstractNode node, Position position, int indent) {
        switch (position) {
            case RIGHT -> {
                Pos startPos = new Pos(
                        node.getFrame().getStopPos().getX() + indent,
                        node.getFrame().getStartPos().getY()
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStopPos().getX() + frame.getWidth() + indent,
                        node.getFrame().getStartPos().getY() + frame.getHeight()
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
            case LEFT -> {
                Pos startPos = new Pos(
                        node.getFrame().getStartPos().getX() - frame.getWidth() - indent,
                        node.getFrame().getStartPos().getY()
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStartPos().getX() - indent,
                        node.getFrame().getStartPos().getY() + frame.getHeight()
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
            case BOTTOM -> {
                Pos startPos = new Pos(
                        node.getFrame().getStartPos().getX(),
                        node.getFrame().getStopPos().getY() + indent
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStartPos().getX() + frame.getWidth(),
                        node.getFrame().getStopPos().getY() + frame.getHeight() + indent
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
            case TOP -> {
                Pos startPos = new Pos(
                        node.getFrame().getStartPos().getX(),
                        node.getFrame().getStartPos().getY() - frame.getHeight() - indent
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStartPos().getX() + frame.getWidth(),
                        node.getFrame().getStartPos().getY() - indent
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNode)) return false;
        AbstractNode node = (AbstractNode) o;
        return id == node.id && Objects.equals(frame, node.frame);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, frame);
    }

    public enum Position {
        LEFT, RIGHT, TOP, BOTTOM
    }
}
