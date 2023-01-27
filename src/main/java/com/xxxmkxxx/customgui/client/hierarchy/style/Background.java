package com.xxxmkxxx.customgui.client.hierarchy.style;

import com.xxxmkxxx.customgui.CustomGUI;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

@Getter @Setter
@Builder
public class Background {
    @Builder.Default
    private MatrixStack matrixStack = new MatrixStack();
    @Builder.Default
    private Type type = Type.COLORED;
    @Builder.Default
    private Color color = Color.builder().build();
    @Builder.Default
    private Opacity opacity = new Opacity(50);
    @Builder.Default
    private Identifier identifier = new Identifier("modid");

    public static <N extends AbstractNode> Consumer<N> chooseBackground(Type type) {
        Consumer<N> renderMethod = (node) -> {};

        switch (type) {
            case IMAGED -> {
                renderMethod = node -> {
                    CustomGUI.NODE_DRAWABLE_HELPER.drawTexture(
                            node.getStyle().getMatrixStack(),
                            node.getFrame(),
                            node.getStyle().getBackground().getIdentifier()
                    );
                };
            }
            case COLORED -> {
                renderMethod = node -> {
                    CustomGUI.NODE_DRAWABLE_HELPER.fillFrame(
                            node.getStyle().getMatrixStack(),
                            node.getFrame(),
                            node.getStyle().getHexBackgroundColor()
                    );
                };
            }
        }

        return renderMethod;
    }

    public enum Type {
        COLORED, IMAGED
    }
}
