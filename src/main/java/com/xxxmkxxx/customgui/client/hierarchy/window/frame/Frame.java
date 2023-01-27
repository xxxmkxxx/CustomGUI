package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public interface Frame {
    boolean checkPosBelongs(int xPos, int yPos);
    boolean checkPosBelongs(Pos pos);
}
