package com.github.fergusstrange.diusshopping.checkout;

import java.math.BigDecimal;
import java.util.Map;

public class InventoryItemsProvider {

    private static Map<String, InventoryItem> inventoryItems = Map.of(
            "ipd", new InventoryItem("ipd", "Super iPad", new BigDecimal("549.99")),
            "mbp", new InventoryItem("mbp", "MacBook Pro", new BigDecimal("1399.99")),
            "atv", new InventoryItem("atv", "Apple TV", new BigDecimal("109.50")),
            "vga", new InventoryItem("vga", "VGA adapter", new BigDecimal("30.00")));

    public static Map<String, InventoryItem> defaultInventoryItems() {
        return inventoryItems;
    }
}
