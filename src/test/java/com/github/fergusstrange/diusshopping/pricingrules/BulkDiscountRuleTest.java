package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.InventoryItem;
import com.github.fergusstrange.diusshopping.checkout.LineItem;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import static com.github.fergusstrange.diusshopping.checkout.LineItem.LineItemBuilder.aLineItem;
import static com.github.fergusstrange.diusshopping.pricingrules.TestFixtures.someLineItems;
import static java.math.BigDecimal.*;

public class BulkDiscountRuleTest {

    @Test
    public void shouldApplyDiscountValue() {
        BulkDiscountRule bulkDiscountRule = new BulkDiscountRule("abc", 2, ONE);

        HashMap<String, LineItem> lineItems = someLineItems();

        Map<String, LineItem> postBulkDiscountLineItems = bulkDiscountRule.pricingRule().apply(lineItems);

        Assertions.assertThat(postBulkDiscountLineItems)
                .containsExactly(new SimpleEntry<>("abc", aLineItem()
                                .inventoryItem(new InventoryItem("abc", "A B C", TEN))
                                .count(2)
                                .discountValue(new BigDecimal("18"))
                                .build()),
                        new SimpleEntry<>("def", aLineItem()
                                .discountValue(ZERO)
                                .count(2)
                                .inventoryItem(new InventoryItem("def", "D E F", new BigDecimal("123.45")))
                                .build()));
    }
}