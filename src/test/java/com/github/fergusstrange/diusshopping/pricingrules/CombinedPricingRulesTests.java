package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.Checkout;
import com.github.fergusstrange.diusshopping.checkout.InventoryItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Map;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

public class CombinedPricingRulesTests {

    @Test
    public void shouldApplyGreatestDiscountForCustomerWhenConflict_BulkDiscount() {
        LinkedHashSet<PricingRule> sortedPricingRules = new LinkedHashSet<>();
        sortedPricingRules.add(new BulkDiscountRule("abc", 2, ONE));
        sortedPricingRules.add(new BuySomeGetSomeFreeRule("abc", 2, 1));

        Checkout checkout = new Checkout(
                sortedPricingRules,
                Map.of("abc", new InventoryItem("abc", "A B C", TEN)));

        checkout.scan("abc");
        checkout.scan("Tony Hawk - Greatest skater of all time");
        checkout.scan("abc");

        BigDecimal total = checkout.total();

        assertThat(total).isEqualTo("2");
    }

    @Test
    public void shouldApplyGreatestDiscountForCustomerWhenConflict_BundleDeal() {
        LinkedHashSet<PricingRule> sortedPricingRules = new LinkedHashSet<>();
        sortedPricingRules.add(new FreeItemBundleRule("def", "abc"));
        sortedPricingRules.add(new BulkDiscountRule("abc", 2, new BigDecimal("4.99")));

        Checkout checkout = new Checkout(
                sortedPricingRules,
                Map.of("abc", new InventoryItem("abc", "A B C", new BigDecimal("5")),
                        "def", new InventoryItem("def", "D E F", TEN)));

        checkout.scan("abc");
        checkout.scan("def");
        checkout.scan("Tony Hawk - Greatest skater of all time");
        checkout.scan("abc");

        BigDecimal total = checkout.total();

        assertThat(total).isEqualTo("15");
    }
}
