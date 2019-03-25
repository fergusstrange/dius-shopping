package com.github.fergusstrange.diusshopping.checkout;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Predicate;

public class LineItems {

    public static Map<String, LineItem> patchedLineItems(Map<String, LineItem> currentLineItems, LineItem lineItem) {
        currentLineItems.put(lineItem.getInventoryItem().getSku(), lineItem);
        return currentLineItems;
    }

    public static BigDecimal lineItemStandardCost(LineItem lineItem) {
        return lineItem.getInventoryItem()
                .getValue()
                .multiply(new BigDecimal(lineItem.getCount()));
    }

    public static Predicate<LineItem> forGreaterDiscount(Map<String, LineItem> currentLineItems, String appliedSku) {
        return updatedLineItem -> updatedLineItem
                .getDiscountValue()
                .compareTo(currentLineItems.get(appliedSku).getDiscountValue()) > 0;
    }
}
