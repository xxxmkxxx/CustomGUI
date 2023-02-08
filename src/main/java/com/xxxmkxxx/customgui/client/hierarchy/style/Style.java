package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Style implements Cloneable {
    private static final Style defaultStyle = new Style();
    private MatrixStack matrixStack = new MatrixStack();
    private Color color = Color.builder().build();
    private Opacity opacity = new Opacity(100);
    private Shadow shadow = Shadow.builder().build();
    private Indent indent = Indent.builder().build();
    private Background background = Background.builder().build();

    static {
        defaultStyle.setColor(Color.DefaultColor.BLACK.getColor());
        defaultStyle.setOpacity(new Opacity(100));
        defaultStyle.setBackground(Background.builder().type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
        defaultStyle.setIndent(new Indent(1, 1, 1, 1));
    }

    public static Style defaultStyle() {
        try {
            return (Style) defaultStyle.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Style(matrixStack, (Color) color.clone(), (Opacity) opacity.clone(), (Shadow) shadow.clone(), (Indent) indent.clone(), (Background) background.clone());
    }

    public int getHexColor() {
        return (int) Long.parseLong(opacity.getHex() + color.getHexValue(), 16);
    }
    public int getHexBackgroundColor() {
        return (int) Long.parseLong(background.getOpacity().getHex() + background.getColor().getHexValue(), 16);
    }
}
