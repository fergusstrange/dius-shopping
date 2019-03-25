package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.LineItem;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.github.fergusstrange.diusshopping.checkout.LineItems.*;
import static java.util.Optional.ofNullable;

public class BulkDiscountRule implements PricingRule {

    private final String appliedSku;
    private final int minimumQuantityToApply;
    private final BigDecimal priceToApply;

    public BulkDiscountRule(String appliedSku, int minimumQuantityToApply, BigDecimal priceToApply) {
        this.appliedSku = appliedSku;
        this.minimumQuantityToApply = minimumQuantityToApply;
        this.priceToApply = priceToApply;
    }

    @Override
    public Function<Map<String, LineItem>, Map<String, LineItem>> pricingRule() {
        return currentLineItems -> ofNullable(currentLineItems.get(appliedSku))
                .filter(lineItem -> lineItem.getCount() >= minimumQuantityToApply)
                .map(this::lineItemWithDiscountedValue)
                .filter(forGreaterDiscount(currentLineItems, appliedSku))
                .map(lineItem -> patchedLineItems(currentLineItems, lineItem))
                .orElse(currentLineItems);
    }

    private LineItem lineItemWithDiscountedValue(LineItem lineItem) {
        return lineItem.toBuilder()
                .discountValue(calculatedDiscountedValue(lineItem))
                .build();
    }

    private BigDecimal calculatedDiscountedValue(LineItem lineItem) {
        BigDecimal totalValueAfterDiscount = priceToApply.multiply(BigDecimal.valueOf(lineItem.getCount()));
        return lineItemStandardCost(lineItem).subtract(totalValueAfterDiscount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkDiscountRule that = (BulkDiscountRule) o;
        return minimumQuantityToApply == that.minimumQuantityToApply &&
                Objects.equals(appliedSku, that.appliedSku) &&
                Objects.equals(priceToApply, that.priceToApply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appliedSku, minimumQuantityToApply, priceToApply);
    }
}
