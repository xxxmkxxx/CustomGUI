package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.Window;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.AbstractFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.button.SimpleButton;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@SuppressWarnings("unused")
public class SimplePane extends AbstractPane {
    private final List<AbstractNode> nodes;

    protected SimplePane(Pos startPos, Pos stopPos) {
        super(startPos, stopPos);
        nodes = new LinkedList<>();
    }

    @Override
    public void initSection(Function<AbstractNode, WindowSection> initMethod) {
        super.initSection(initMethod);
        nodes.forEach(node -> node.initSection(initMethod));
    }

    @Override
    public void init(Consumer<AbstractNode> initMethod) {
        super.init(initMethod);
        nodes.forEach(node -> node.init(initMethod));
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        this.renderer = new RendererFactory().create(type);
        nodes.forEach(node -> node.initRenderer(rendererType));
    }

    @Override
    public void scaling(Window window) {
        super.scaling(window);
        nodes.forEach(node -> node.scaling(window));
    }

    @Override
    public void hide() {
        super.hide();
        nodes.forEach(AbstractNode::hide);
    }

    @Override
    public void display() {
        super.display();
        nodes.forEach(AbstractNode::display);

    }

    public void addNode(AbstractNode node) {
        Validator.checkNullObject(node);
        nodes.add(node);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Pos startPos;
        private Pos stopPos;
        private Style style;

        public Builder() {
            this.startPos = Pos.defaultPos();
            this.style = Style.defaultStyle();
        }

        public Builder startPos(Pos pos) {
            try {
                this.startPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder stopPos(Pos pos) {
            try {
                this.stopPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder positions(Pos startPos, Pos stopPos) {
            startPos(startPos);
            stopPos(stopPos);
            return this;
        }

        public Builder positions(AbstractFrame frame) {
            startPos(frame.getStartPos());
            stopPos(frame.getStopPos());
            return this;
        }

        public Builder style(Style style) {
            try {
                this.style = (Style) style.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public SimplePane build() {
            Pos stopPos = this.stopPos == null
                    ? Pos.builder().relativeCoords(startPos.getXIndentPercent() + 10, startPos.getYIndentPercent() + 15).build(startPos.getXPercentValue(), startPos.getYPercentValue())
                    : this.stopPos;

            SimplePane simplePane = new SimplePane(startPos, stopPos);
            simplePane.setStyle(style);
            return simplePane;
        }
    }

    public static class RendererFactory implements NodeRendererFactory<SimplePane> {
        private final ParametrizedSelfDestructionMethod<SimplePane> initBackgroundMethod = new ParametrizedSelfDestructionMethod<>();
        private Consumer<SimplePane> renderBackgroundMethod;

        public RendererFactory() {
            initBackgroundMethod.setAction(simplePane -> {
                renderBackgroundMethod = Background.chooseBackground(simplePane.getStyle().getBackground().getType());
            });
        }

        @Override
        public NodeRenderer<SimplePane> create(RendererType type) {
            return this::render;
        }


        @SuppressWarnings("unchecked")
        private void render(SimplePane simplePane) {
            initBackgroundMethod.execute(simplePane);

            renderBackgroundMethod.accept(simplePane);

            simplePane.getNodes().forEach(node -> node.getState().execute(node, node.getRenderer()));
        }
    }
}