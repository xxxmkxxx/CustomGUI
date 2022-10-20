package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.target.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.ClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.deselection.DeselectionEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.selection.SelectionEventHandler;
import lombok.Getter;
import net.minecraft.text.Text;

@Getter
public abstract class AbstractButton extends AbstractNode implements Button, ClickEventHandler, SelectionEventHandler, DeselectionEventHandler {
    protected final Text name;

    public AbstractButton(Text name) {
        this.name = name;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDeselect() {

    }

    @Override
    public void onSelect() {

    }

    @Override
    public void updateTarget(int xPos, int yPos, TargetManager targetManager) {

    }
}