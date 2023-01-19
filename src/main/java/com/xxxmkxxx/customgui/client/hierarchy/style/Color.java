package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Color {
    @Builder.Default
    private String hexValue = DefaultColor.BLACK.getColor().getHexValue();


    @Getter
    public enum DefaultColor {
        BLACK("000000"), RED("ff0000"), BLUE("001aff"),
        GREEN("50eb58"), YELLOW("ddff00"), ORANGE("ff9900");

        DefaultColor(String hex) {
            this.color = new Color(hex);
        }

        private final Color color;
    }
}
