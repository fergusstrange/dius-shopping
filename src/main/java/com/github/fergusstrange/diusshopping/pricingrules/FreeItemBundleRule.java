package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.LineItem;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.github.fergusstrange.diusshopping.checkout.LineItems.forGreaterDiscount;
import static java.lang.Math.min;
import static java.util.Collections.unmodifiableCollection;

public class FreeItemBundleRule implements PricingRule {

    private final String qualifyingPurchaseSku;
    private final String bundledItemSku;

    private final Collection<String> skuKeys;

    public FreeItemBundleRule(String qualifyingPurchaseSku, String bundledItemSku) {
        this.qualifyingPurchaseSku = qualifyingPurchaseSku;
        this.bundledItemSku = bundledItemSku;

        this.skuKeys = unmodifiableCollection(List.of(qualifyingPurchaseSku, bundledItemSku));
    }

    @Override
    public Function<Map<String, LineItem>, Map<String, LineItem>> pricingRule() {
        return lineItems -> lineItems.keySet()
                .containsAll(skuKeys) ?
                patchedMapWithGreaterDiscountedBundledItem(lineItems) :
                lineItems;
    }

    private Map<String, LineItem> patchedMapWithGreaterDiscountedBundledItem(Map<String, LineItem> currentLineItems) {
        LineItem qualifyingPurchaseLineItem = currentLineItems.get(qualifyingPurchaseSku);
        LineItem bundledLineItem = currentLineItems.get(bundledItemSku);

        int bundleDiscountQuantity = min(qualifyingPurchaseLineItem.getCount(),
                bundledLineItem.getCount());

        BigDecimal discountValue = bundledQuantityDiscount(bundledLineItem, bundleDiscountQuantity);

        LineItem updatedLineItem = bundledLineItem.toBuilder()
                .discountValue(discountValue)
                .build();

        if (forGreaterDiscount(currentLineItems, bundledItemSku).test(updatedLineItem)) {
            currentLineItems.put(bundledItemSku, updatedLineItem);
        }

        return currentLineItems;
    }

    private BigDecimal bundledQuantityDiscount(LineItem bundledLineItem, int bundleDiscountQuantity) {
        return bundledLineItem.getInventoryItem()
                .getValue()
                .multiply(new BigDecimal(bundleDiscountQuantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FreeItemBundleRule that = (FreeItemBundleRule) o;
        return Objects.equals(qualifyingPurchaseSku, that.qualifyingPurchaseSku) &&
                Objects.equals(bundledItemSku, that.bundledItemSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualifyingPurchaseSku, bundledItemSku);
    }
}
