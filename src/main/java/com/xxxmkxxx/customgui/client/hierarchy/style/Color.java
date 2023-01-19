package com.xxxmkxxx.customgui.client.hierarchy.style;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Color {
    @Builder.Default
    private String hexColor = Default.BLACK.getHex();

    @Getter
    @AllArgsConstructor
    public enum Default {
        BLACK("000000"), RED("ff0000"), BLUE("001aff"),
        GREEN("50eb58"), YELLOW("ddff00"), ORANGE("ff9900");

        private final String hex;
    }
}
