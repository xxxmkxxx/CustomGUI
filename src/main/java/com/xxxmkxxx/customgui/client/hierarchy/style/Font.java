package com.xxxmkxxx.customgui.client.hierarchy.style;

import com.xxxmkxxx.customgui.client.common.Scalable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.Identifier;

@Getter
@AllArgsConstructor
public class Font implements Cloneable, Scalable {
    private Identifier identifier;
    private float xSizePx;
    private float ySizePx;
    private float xSizePercent;
    private float ySizePercent;
    private Color color;
    private Align align;
    private float symbolPaddingPx;
    private float symbolPaddingPercent;
    private Opacity opacity;

    @Override
    public Font clone() throws CloneNotSupportedException {
        return new Font(identifier, xSizePx, ySizePx, xSizePercent, ySizePercent, (Color) color.clone(), align, symbolPaddingPx, symbolPaddingPercent, opacity);
    }

    public int getHexColor() {
        return (int) Long.parseLong(opacity.getHex() + color.getHexValue(), 16);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void scaling(float xPercentValue, float yPercentValue) {
        xSizePx = xPercentValue * xSizePercent;
        ySizePx = yPercentValue * ySizePercent;
        symbolPaddingPx = (int) (xPercentValue * symbolPaddingPercent);
    }

    enum SizeType {
        LARGEST, NORMAL, SMALL
    }

    public static class Builder {
        private Identifier identifier;
        private float xSizePx;
        private float ySizePx;
        private float xSizePercent;
        private float ySizePercent;
        private Color color;
        private Align align;
        private float symbolPaddingPX;
        private float symbolPaddingPercent;
        private Opacity opacity;

        public Builder() {
            //gag
            this.identifier = new Identifier("");
            this.xSizePercent = Float.MIN_VALUE;
            this.ySizePercent = Float.MIN_VALUE;
            this.symbolPaddingPercent = Float.MIN_VALUE;
            this.xSizePx = 6.0f;
            this.ySizePx = 6.0f;
            this.color = Color.DefaultColor.BLACK.getColor();
            this.align = Align.LEFT;
            this.symbolPaddingPX = 1.0f;
            this.opacity = new Opacity(100);
        }

        public Builder xSize(float xSizePx) {
            this.xSizePx = xSizePx;
            return this;
        }

        public Builder ySize(float ySizePx) {
            this.ySizePx = ySizePx;
            return this;
        }

        public Builder size(float size) {
            xSize(size);
            ySize(size);
            return this;
        }

        public Builder xSizePercent(float xSizePercent) {
            this.xSizePercent = xSizePercent;
            return this;
        }

        public Builder ySizePercent(float ySizePercent) {
            this.ySizePercent = ySizePercent;
            return this;
        }

        public Builder sizePercents(float percent) {
            xSizePercent(percent);
            ySizePercent(percent);
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder symbolPadding(float symbolPaddingPX) {
            this.symbolPaddingPX = symbolPaddingPX;
            return this;
        }

        public Builder symbolPaddingPercent(float symbolPaddingPercent) {
            this.symbolPaddingPercent = symbolPaddingPercent;
            return this;
        }

        public Font build(float xPercentValue, float yPercentValue) {
            float xSizePercent = this.xSizePercent == Float.MIN_VALUE ? xSizePx / xPercentValue : this.xSizePercent;
            float ySizePercent = this.ySizePercent == Float.MIN_VALUE ? ySizePx / yPercentValue : this.ySizePercent;
            float symbolPaddingPercent = this.symbolPaddingPercent == Float.MIN_VALUE ? symbolPaddingPX / xPercentValue : this.symbolPaddingPercent;

            this.xSizePercent = xSizePercent;
            this.ySizePercent = ySizePercent;
            this.symbolPaddingPercent = symbolPaddingPercent;

            return new Font(
                    identifier,
                    xSizePx, ySizePx,
                    xSizePercent, ySizePercent,
                    color, align,
                    symbolPaddingPX, symbolPaddingPercent,
                    opacity
            );
        }
    }
}