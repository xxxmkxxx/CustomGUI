package com.xxxmkxxx.customgui.client.hierarchy.node.target;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import lombok.Getter;

public class TargetManager {
    @Getter
    private AbstractNode currentSelection;
    @Getter
    private AbstractNode lastSelection;

    public void setCurrentSelection(AbstractNode currentSelection) {
        this.lastSelection = this.currentSelection;
        this.currentSelection = currentSelection;
    }
}
