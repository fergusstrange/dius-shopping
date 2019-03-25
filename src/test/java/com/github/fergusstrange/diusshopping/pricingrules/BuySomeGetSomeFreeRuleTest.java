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

public class BuySomeGetSomeFreeRuleTest {

    @Test
    public void shouldApplyDiscountValue() {
        BuySomeGetSomeFreeRule buySomeGetSomeFreeRule = new BuySomeGetSomeFreeRule("abc", 2, 2);

        HashMap<String, LineItem> lineItems = someLineItems();

        Map<String, LineItem> postBulkDiscountLineItems = buySomeGetSomeFreeRule.pricingRule().apply(lineItems);

        Assertions.assertThat(postBulkDiscountLineItems)
                .containsExactly(new AbstractMap.SimpleEntry<>("abc", aLineItem()
                                .inventoryItem(new InventoryItem("abc", "A B C", TEN))
                                .count(2)
                                .discountValue(new BigDecimal("20"))
                                .build()),
                        new AbstractMap.SimpleEntry<>("def", aLineItem()
                                .discountValue(ZERO)
                                .count(2)
                                .inventoryItem(new InventoryItem("def", "D E F", new BigDecimal("123.45")))
                                .build()));
    }
}