package com.xxxmkxxx.customgui.client.common.inventory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.inventory.Inventory;

@Getter
@RequiredArgsConstructor
public abstract class AbstractInventory implements Inventory {
    protected final InventoryType type;
}
