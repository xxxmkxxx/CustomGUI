package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.LeftClickEventHandler;
import lombok.Getter;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public abstract class AbstractButton extends AbstractNode implements Button, LeftClickEventHandler {
    protected final Text name;
    protected final Queue<Runnable> leftClickActions = new LinkedList<>();

    public AbstractButton(Text name) {
        this.name = name;
    }

    public void addLeftClickAction(Runnable action) {
        leftClickActions.add(action);
    }

    @Override
    public void onLeftClick() {
        leftClickActions.forEach(Runnable::run);
    }
}