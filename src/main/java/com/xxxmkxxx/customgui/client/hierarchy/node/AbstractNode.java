package com.xxxmkxxx.customgui.client.hierarchy.node;

import com.xxxmkxxx.customgui.client.hierarchy.node.target.Section;
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
    private static int currentIdNumber = 0;
    private final int id;
    public static final AbstractNode EMPTY_NODE = new AbstractNode() {
        @Override
        public void initRenderer(RendererType type) {

        }
    };

    protected AbstractFrame frame = AbstractFrame.DEFAULT_FRAME;
    @Getter
    protected Section section = Section.MIXED;
    @Setter
    protected Style style = new Style();
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
        state = States.HIDED;
    }

    public void initRenderer(RendererType type) {
        this.rendererType = type;
    }

    public void initSection(Function<AbstractNode, Section> initMethod) {
        this.section = initMethod.apply(this);
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
                        node.getFrame().getStopPos().x() + indent,
                        node.getFrame().getStartPos().y()
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStopPos().x() + frame.getWidth() + indent,
                        node.getFrame().getStartPos().y() + frame.getHeight()
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
            case LEFT -> {
                Pos startPos = new Pos(
                        node.getFrame().getStartPos().x() - frame.getWidth() - indent,
                        node.getFrame().getStartPos().y()
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStartPos().x() - indent,
                        node.getFrame().getStartPos().y() + frame.getHeight()
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
            case BOTTOM -> {
                Pos startPos = new Pos(
                        node.getFrame().getStartPos().x(),
                        node.getFrame().getStopPos().y() + indent
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStartPos().x() + frame.getWidth(),
                        node.getFrame().getStopPos().y() + frame.getHeight() + indent
                );

                this.frame = new SimpleFrame(startPos, stopPos);
            }
            case TOP -> {
                Pos startPos = new Pos(
                        node.getFrame().getStartPos().x(),
                        node.getFrame().getStartPos().y() - frame.getHeight() - indent
                );

                Pos stopPos = new Pos(
                        node.getFrame().getStartPos().x() + frame.getWidth(),
                        node.getFrame().getStartPos().y() - indent
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
