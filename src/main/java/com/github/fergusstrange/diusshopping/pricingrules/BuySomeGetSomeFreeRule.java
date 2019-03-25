package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.LineItem;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.github.fergusstrange.diusshopping.checkout.LineItems.forGreaterDiscount;
import static com.github.fergusstrange.diusshopping.checkout.LineItems.patchedLineItems;
import static java.math.RoundingMode.DOWN;
import static java.util.Optional.ofNullable;

public class BuySomeGetSomeFreeRule implements PricingRule {

    private final String appliedSku;
    private final int requiredQuantityOfItemPurchased;
    private final int quantityOfFreeItemGiven;

    public BuySomeGetSomeFreeRule(String appliedSku, int requiredQuantityOfItemPurchased, int quantityOfFreeItemGiven) {
        this.appliedSku = appliedSku;
        this.requiredQuantityOfItemPurchased = requiredQuantityOfItemPurchased;
        this.quantityOfFreeItemGiven = quantityOfFreeItemGiven;
    }

    @Override
    public Function<Map<String, LineItem>, Map<String, LineItem>> pricingRule() {
        return currentLineItems -> ofNullable(currentLineItems.get(appliedSku))
                .filter(lineItem -> lineItem.getCount() >= requiredQuantityOfItemPurchased)
                .map(lineItem -> lineItem.toBuilder()
                        .discountValue(calculatedDiscountedValue(lineItem))
                        .build())
                .filter(forGreaterDiscount(currentLineItems, appliedSku))
                .map(lineItem -> patchedLineItems(currentLineItems, lineItem))
                .orElse(currentLineItems);
    }

    private BigDecimal calculatedDiscountedValue(LineItem lineItem) {
        BigDecimal discountQuantity = new BigDecimal(lineItem.getCount())
                .divide(new BigDecimal(requiredQuantityOfItemPurchased), DOWN);

        return lineItem.getInventoryItem()
                .getValue()
                .multiply(discountQuantity)
                .multiply(new BigDecimal(quantityOfFreeItemGiven));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuySomeGetSomeFreeRule that = (BuySomeGetSomeFreeRule) o;
        return requiredQuantityOfItemPurchased == that.requiredQuantityOfItemPurchased &&
                quantityOfFreeItemGiven == that.quantityOfFreeItemGiven &&
                Objects.equals(appliedSku, that.appliedSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appliedSku, requiredQuantityOfItemPurchased, quantityOfFreeItemGiven);
    }
}
