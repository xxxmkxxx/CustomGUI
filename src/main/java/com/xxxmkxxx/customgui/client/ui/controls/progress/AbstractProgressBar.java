package com.xxxmkxxx.customgui.client.ui.controls.progress;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.style.Style;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import lombok.Getter;

@Getter
public abstract class AbstractProgressBar extends AbstractNode implements ProgressBar {
    protected float progress;
    protected float progressPercent;
    protected float progressPercentValue;

    public AbstractProgressBar(Pos startPos, Pos stopPos, float progressPercent, float progressPercentValue, Style style) {
        this.progressPercent = progressPercent;
        this.progressPercentValue = progressPercentValue;
        this.frame = SimpleFrame.builder().positions(startPos, stopPos).build();
        this.style = style;
        updateProgress();
    }

    private void updateProgress() {
        progress = progressPercent * progressPercentValue;
    }

    @Override
    public void moveProgress(float percent) {
        progressPercent += percent;
        updateProgress();
    }
}
