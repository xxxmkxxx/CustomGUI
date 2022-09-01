package com.xxxmkxxx.customgui.client.geometry.frame;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;

public interface Frame {
    boolean isPosBelongs(int xPos, int yPos);
    boolean isPosBelongs(Pos pos);
}
