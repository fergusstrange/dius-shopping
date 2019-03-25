package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.InventoryItem;
import com.github.fergusstrange.diusshopping.checkout.LineItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static com.github.fergusstrange.diusshopping.checkout.LineItem.LineItemBuilder.aLineItem;
import static com.github.fergusstrange.diusshopping.pricingrules.TestFixtures.someLineItems;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

public class FreeItemBundleRuleTest {

    @Test
    public void shouldApplyDiscountValue() {
        FreeItemBundleRule freeItemBundleRule = new FreeItemBundleRule("abc", "def");

        HashMap<String, LineItem> lineItems = someLineItems();

        Map<String, LineItem> postBulkDiscountLineItems = freeItemBundleRule.pricingRule().apply(lineItems);

        Assertions.assertThat(postBulkDiscountLineItems)
                .containsExactly(new AbstractMap.SimpleEntry<>("abc", aLineItem()
                                .inventoryItem(new InventoryItem("abc", "A B C", TEN))
                                .count(2)
                                .discountValue(ZERO)
                                .build()),
                        new AbstractMap.SimpleEntry<>("def", aLineItem()
                                .discountValue(new BigDecimal("246.90"))
                                .count(2)
                                .inventoryItem(new InventoryItem("def", "D E F", new BigDecimal("123.45")))
                                .build()));
    }
}