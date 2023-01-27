package com.xxxmkxxx.customgui.client.ui.controls.slot;

import com.xxxmkxxx.customgui.client.common.util.Utils;
import com.xxxmkxxx.customgui.client.hierarchy.renderer.RendererType;
import com.xxxmkxxx.customgui.client.hierarchy.window.frame.SimpleFrame;
import com.xxxmkxxx.customgui.client.hierarchy.node.AbstractNode;
import com.xxxmkxxx.customgui.client.hierarchy.window.position.Pos;
import com.xxxmkxxx.customgui.client.ui.controls.text.SimpleText;
import lombok.Getter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

@Getter
public abstract class AbstractSlot extends AbstractNode implements Slot {
    protected SimpleText amountItemsText;
    protected int index;
    protected final Inventory inventory;

    public AbstractSlot(int index, Inventory inventory, SimpleFrame frame) {
        this.index = index;
        this.inventory = inventory;
        this.frame = frame;
    }

    @Override
    public void scaling(double widthScaleValue, double heightScaleValue) {
        super.scaling(widthScaleValue, heightScaleValue);
        amountItemsText.scaling(widthScaleValue, heightScaleValue);
    }

    @Override
    public void initRenderer(RendererType type) {
        super.initRenderer(type);
        amountItemsText.initRenderer(type);
    }

    protected void updateAmountItemsText(ItemStack itemStack, SimpleFrame frame) {
        Text amount = Text.of(Integer.toString(itemStack.getCount()));

        Pos startPos = new Pos(
                frame.getStopPos().x() - Utils.getTextWidth(amount),
                frame.getStopPos().y() - Utils.getTextHeight()
        );

        Pos stopPos = new Pos(
                startPos.x() + Utils.getTextWidth(amount),
                frame.getStopPos().y() + Utils.getTextHeight()
        );

        amountItemsText.getFrame().setStartPos(startPos);
        amountItemsText.getFrame().setStopPos(stopPos);
        amountItemsText.setText(amount);
    }

    public ItemStack getItemStack() {
        return inventory.getStack(index);
    }

    public boolean isStandardItem() {
        return isStandardItem(getItemStack().getItem());
    }

    public boolean isStandardItem(Item item) {
        return Registry.ITEM.getId(item).getNamespace().equals("minecraft");
    }
}
