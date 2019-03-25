package com.github.fergusstrange.diusshopping.checkout;

import java.math.BigDecimal;
import java.util.Objects;

public class InventoryItem {

    private final String sku;
    private final String name;
    private final BigDecimal value;

    public InventoryItem(String sku, String name, BigDecimal value) {
        this.sku = sku;
        this.name = name;
        this.value = value;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem inventoryItem = (InventoryItem) o;
        return Objects.equals(sku, inventoryItem.sku) &&
                Objects.equals(name, inventoryItem.name) &&
                Objects.equals(value, inventoryItem.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, name, value);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
