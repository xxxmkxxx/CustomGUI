package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

@Getter @Setter
public class Style {
    private MatrixStack matrixStack = new MatrixStack();
    private Color color = Color.builder().build();
    private Opacity opacity = new Opacity(100);
    private Shadow shadow = Shadow.builder().build();
    private Indent indent = Indent.builder().build();
    private Background background = Background.builder().build();

    public int getHexColor() {
        return (int) Long.parseLong(opacity.getHex() + color.getHexValue(), 16);
    }
    public int getHexBackgroundColor() {
        return (int) Long.parseLong(background.getOpacity().getHex() + background.getColor().getHexValue(), 16);
    }
}
