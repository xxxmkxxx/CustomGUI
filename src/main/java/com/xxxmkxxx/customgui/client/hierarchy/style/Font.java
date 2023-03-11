package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Builder;
import lombok.Getter;
import net.minecraft.util.Identifier;

@Getter
@Builder
public class Font implements Cloneable {
    //gag
    @Builder.Default
    private Identifier identifier = new Identifier("");
    @Builder.Default
    private int sizePx = 6;
    @Builder.Default
    private double sizePercent = 1.5d;
    @Builder.Default
    private SizeType sizeType = SizeType.NORMAL;
    @Builder.Default
    private Color color = Color.DefaultColor.BLACK.getColor();
    @Builder.Default
    private Align align = Align.LEFT;
    @Builder.Default
    private int symbolPaddingPX = 1;
    @Builder.Default
    private double symbolPaddingPercent = 0.4d;

    @Override
    public Font clone() throws CloneNotSupportedException {
        return new Font(identifier, sizePx, sizePercent, sizeType, (Color) color.clone(), align, symbolPaddingPX, symbolPaddingPercent);
    }

    enum SizeType {
        LARGEST, NORMAL, SMALL
    }
}
