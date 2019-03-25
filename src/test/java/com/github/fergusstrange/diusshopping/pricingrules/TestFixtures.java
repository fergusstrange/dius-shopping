package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.InventoryItem;
import com.github.fergusstrange.diusshopping.checkout.LineItem;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.github.fergusstrange.diusshopping.checkout.LineItem.LineItemBuilder.aLineItem;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

public class TestFixtures {

    public static HashMap<String, LineItem> someLineItems() {
        HashMap<String, LineItem> lineItems = new HashMap<>();
        lineItems.put("abc", aLineItem()
                .discountValue(ZERO)
                .count(2)
                .inventoryItem(new InventoryItem("abc", "A B C", TEN))
                .build());
        lineItems.put("def", aLineItem()
                .discountValue(ZERO)
                .count(2)
                .inventoryItem(new InventoryItem("def", "D E F", new BigDecimal("123.45")))
                .build());
        return lineItems;
    }
}
