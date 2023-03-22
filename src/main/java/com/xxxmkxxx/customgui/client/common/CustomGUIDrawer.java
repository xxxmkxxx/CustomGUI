package com.xxxmkxxx.customgui.client.common;

import com.xxxmkxxx.customgui.client.hierarchy.style.Font;
import net.minecraft.client.font.FontStorage;

public interface CustomGUIDrawer {
    default boolean accept(FontStorage fontStorage, Font font, int codePoint) {return false;}
}
