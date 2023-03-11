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
    private int scaledFactorPercents = 100;
    private Color color = Color.builder().build();
    private Opacity opacity = new Opacity(100);
    private Shadow shadow = Shadow.builder().build();
    private Margins margins = Margins.builder().build();
    private Paddings paddings = Paddings.builder().build();
    private Background background = Background.builder().build();

    static {
        defaultStyle.setColor(Color.DefaultColor.BLACK.getColor());
        defaultStyle.setOpacity(new Opacity(100));
        defaultStyle.setBackground(Background.builder().type(Background.Type.COLORED).color(Color.DefaultColor.BLUE.getColor()).build());
        defaultStyle.setMargins(new Margins(0, 0, 0, 0));
        defaultStyle.setPaddings(new Paddings(1, 1, 1, 1));
    }

    public static Style defaultStyle() {
        try {
            return (Style) defaultStyle.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Style(matrixStack, scaledFactorPercents, (Color) color.clone(), (Opacity) opacity.clone(), (Shadow) shadow.clone(), (Margins) margins.clone(), paddings.clone(), (Background) background.clone());
    }

    public int getHexColor() {
        return (int) Long.parseLong(opacity.getHex() + color.getHexValue(), 16);
    }
    public int getHexBackgroundColor() {
        return (int) Long.parseLong(background.getOpacity().getHex() + background.getColor().getHexValue(), 16);
    }
}
