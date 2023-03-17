package com.xxxmkxxx.customgui.client.hierarchy.style;

import com.xxxmkxxx.customgui.client.common.Scalable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.Identifier;

@Getter
@AllArgsConstructor
public class Font implements Cloneable, Scalable {
    private Identifier identifier;
    private int xSizePx;
    private int ySizePx;
    private double xSizePercent;
    private double ySizePercent;
    private Color color;
    private Align align;
    private int symbolPaddingPx;
    private double symbolPaddingPercent;
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
    public void scaling(double xPercentValue, double yPercentValue) {
        xSizePx = (int) (xPercentValue * xSizePercent);
        ySizePx = (int) (yPercentValue * ySizePercent);
        symbolPaddingPx = (int) (xPercentValue * symbolPaddingPercent);
    }

    enum SizeType {
        LARGEST, NORMAL, SMALL
    }

    public static class Builder {
        private Identifier identifier;
        private int xSizePx;
        private int ySizePx;
        private double xSizePercent;
        private double ySizePercent;
        private Color color;
        private Align align;
        private int symbolPaddingPX;
        private double symbolPaddingPercent;
        private Opacity opacity;

        public Builder() {
            //gag
            this.identifier = new Identifier("");
            this.xSizePx = 6;
            this.ySizePx = 6;
            this.color = Color.DefaultColor.BLACK.getColor();
            this.align = Align.LEFT;
            this.symbolPaddingPX = 1;
            this.opacity = new Opacity(100);
        }

        public Builder xSize(int xSizePx) {
            this.xSizePx = xSizePx;
            return this;
        }

        public Builder ySize(int ySizePx) {
            this.ySizePx = ySizePx;
            return this;
        }

        public Builder size(int size) {
            xSize(size);
            ySize(size);
            return this;
        }

        public Builder xSizePercent(double xSizePercent) {
            this.xSizePercent = xSizePercent;
            return this;
        }

        public Builder ySizePercent(double ySizePercent) {
            this.ySizePercent = ySizePercent;
            return this;
        }

        public Builder sizePercents(double percent) {
            xSizePercent(percent);
            ySizePercent(percent);
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder symbolPadding(int symbolPaddingPX) {
            this.symbolPaddingPX = symbolPaddingPX;
            return this;
        }

        public Builder symbolPaddingPercent(double symbolPaddingPercent) {
            this.symbolPaddingPercent = symbolPaddingPercent;
            return this;
        }

        public Font build(double xPercentValue, double yPercentValue) {
            double xSizePercent = this.xSizePercent == 0.0 ? xSizePx / xPercentValue : this.xSizePercent;
            double ySizePercent = this.ySizePercent == 0.0 ? ySizePx / yPercentValue : this.ySizePercent;
            double symbolPaddingPercent = this.symbolPaddingPercent == 0.0 ? symbolPaddingPX / xPercentValue : this.symbolPaddingPercent;

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
