package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.Getter;

@Getter
public class Opacity {
    private int percent = 100;
    private final float percentValue = 2.55f;
    private String hex = "FF";

    public Opacity(int percent) {
        this.percent = percent;
        setPercent(percent);
    }

    public void setPercent(int percent) {
        this.percent = percent;

        if (percent == 100) this.hex = "FF";
        else if (percent == 0) this.hex = "00";
        else this.hex = calculateHex(percent);
    }

    private String calculateHex(int percent) {
        float value = percentValue * percent;
        return Integer.toHexString((int) value);
    }
}
