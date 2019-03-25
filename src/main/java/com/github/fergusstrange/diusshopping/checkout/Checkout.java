package com.github.fergusstrange.diusshopping.checkout;

import com.github.fergusstrange.diusshopping.pricingrules.PricingRule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.github.fergusstrange.diusshopping.checkout.LineItem.LineItemBuilder.aLineItem;
import static com.github.fergusstrange.diusshopping.checkout.LineItems.lineItemStandardCost;
import static java.lang.Math.incrementExact;
import static java.math.BigDecimal.ZERO;
import static java.util.Optional.ofNullable;

public class Checkout {

    private final Set<PricingRule> pricingRules;
    private final Map<String, InventoryItem> inventoryItems;

    private final Map<String, LineItem> lineItems = new HashMap<>();

    public Checkout(Set<PricingRule> pricingRules, Map<String, InventoryItem> inventoryItems) {
        this.pricingRules = pricingRules;
        this.inventoryItems = inventoryItems;
    }

    public void scan(String sku) {
        ofNullable(inventoryItems.get(sku))
                .map(inventoryItem -> existingLineItemOrElseDefault(sku, inventoryItem))
                .map(this::incrementQuantity)
                .ifPresent(incrementedLineItem -> lineItems.put(sku, incrementedLineItem));
    }

    public BigDecimal total() {
        return combinePricingRulesToOneRuleSet()
                .apply(lineItems)
                .values()
                .stream()
                .map(this::lineItemTotal)
                .reduce(BigDecimal::add)
                .orElse(ZERO);
    }

    private Function<Map<String, LineItem>, Map<String, LineItem>> combinePricingRulesToOneRuleSet() {
        return pricingRules.stream()
                .map(PricingRule::pricingRule)
                .reduce(Function.identity(), Function::andThen);
    }

    private LineItem incrementQuantity(LineItem lineItem) {
        return lineItem.toBuilder()
                .count(incrementExact(lineItem.getCount()))
                .build();
    }

    private LineItem existingLineItemOrElseDefault(String sku, InventoryItem inventoryItem) {
        return lineItems.getOrDefault(sku, aLineItem()
                .inventoryItem(inventoryItem)
                .count(0)
                .discountValue(ZERO)
                .build());
    }

    private BigDecimal lineItemTotal(LineItem lineItem) {
        return lineItemStandardCost(lineItem)
                .subtract(lineItem.getDiscountValue());
    }
}
