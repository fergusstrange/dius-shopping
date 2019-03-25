package com.github.fergusstrange.diusshopping.pricingrules;

import com.github.fergusstrange.diusshopping.checkout.LineItem;

import java.util.Map;
import java.util.function.Function;

public interface PricingRule {

    Function<Map<String, LineItem>, Map<String, LineItem>> pricingRule();
}
