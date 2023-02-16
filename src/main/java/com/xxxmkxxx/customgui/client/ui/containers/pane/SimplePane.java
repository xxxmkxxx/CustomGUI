package com.xxxmkxxx.customgui.client.ui.containers.pane;

import com.xxxmkxxx.customgui.client.common.ParametrizedSelfDestructionMethod;
import com.xxxmkxxx.customgui.client.common.Validator;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRenderer;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.NodeRendererFactory;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.style.Background;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.WindowSection;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@SuppressWarnings("unused")
public class SimplePane extends AbstractPane {
    private final List<AbstractNode> nodes;

    protected SimplePane(Pos startPos, int width, int height) {
        super(startPos, width, height);
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
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        nodes.forEach(node -> node.scaling(widthScaleValue, heightScaleValue));
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
        private Pos startPos = Pos.defaultPos();
        private int width = 50;
        private int height = 50;
        private Style style = Style.defaultStyle();

        public Builder pos(Pos pos) {
            try {
                this.startPos = (Pos) pos.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
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
            SimplePane simplePane = new SimplePane(startPos, width, height);
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