package com.xxxmkxxx.customgui.client.ui.controls.button;

import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.node.TargetManager;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.ClickEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.click.ClickEventHandler;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.target.TargetEvent;
import com.xxxmkxxx.customgui.client.hierarchy.node.events.target.TargetEventHandler;
import lombok.Getter;
import net.minecraft.text.Text;

import java.util.LinkedList;

@Getter
public abstract class AbstractButton extends AbstractNode implements Button, TargetEventHandler, ClickEventHandler {
    protected final Text name;
    protected final LinkedList<TargetEvent> targetEvents = new LinkedList<>();
    protected final LinkedList<ClickEvent> clickEvents = new LinkedList<>();


    public AbstractButton(Text name) {
        this.name = name;
    }

    @Override
    public void addEvent(TargetEvent event) {
        targetEvents.add(event);
    }

    @Override
    public void addEvent(ClickEvent event) {
        clickEvents.add(event);
    }

    @Override
    public void callAllTargetedElements() {
        targetEvents.forEach(TargetEvent::onTarget);
    }

    @Override
    public void callAllClickedEvents() {
        clickEvents.forEach(ClickEvent::onClick);
    }

    @Override
    public void updateTarget(int xPos, int yPos, TargetManager targetManager) {
        super.updateTarget(xPos, yPos, targetManager);
        if (isTarget) callAllTargetedElements();
    }
}