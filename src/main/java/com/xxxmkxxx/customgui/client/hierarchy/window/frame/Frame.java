package com.xxxmkxxx.customgui.client.hierarchy.window.frame;

import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;

public interface Frame {
    boolean checkPosBelongs(float xPos, float yPos);
    boolean checkPosBelongs(Pos pos);
}
