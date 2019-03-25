package com.github.fergusstrange.diusshopping.pricingrules;

import java.math.BigDecimal;
import java.util.Set;

public class PricingRuleProvider {

    private static Set<PricingRule> pricingRules = Set.of(
            new BuySomeGetSomeFreeRule("atv", 3, 1),
            new BulkDiscountRule("ipd", 5, new BigDecimal("499.99")),
            new FreeItemBundleRule("mbp", "vga"));

    public static Set<PricingRule> defaultPricingRules() {
        return pricingRules;
    }
}
