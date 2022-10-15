package com.xxxmkxxx.customgui.client.geometry.frame;

import com.xxxmkxxx.customgui.client.geometry.position.Pos;

public interface Frame {
    boolean checkPosBelongs(int xPos, int yPos);
    boolean checkPosBelongs(Pos pos);
}
