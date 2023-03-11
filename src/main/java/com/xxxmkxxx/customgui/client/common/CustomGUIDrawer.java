package com.xxxmkxxx.customgui.client.common;

import net.minecraft.client.font.FontStorage;

public interface CustomGUIDrawer {
    default boolean accept(FontStorage fontStorage, float symbolWidth, float symbolHeight, int codePoint) {return false;}
}
