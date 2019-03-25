package com.github.fergusstrange.diusshopping.checkout;

import static com.github.fergusstrange.diusshopping.checkout.InventoryItemsProvider.defaultInventoryItems;
import static com.github.fergusstrange.diusshopping.pricingrules.PricingRuleProvider.defaultPricingRules;

public class CheckoutProvider {

    //Todo: Replace this all with an injection framework and load this data from config file or persisted datasource
    public static Checkout newCheckout() {
        return new Checkout(defaultPricingRules(), defaultInventoryItems());
    }
}
