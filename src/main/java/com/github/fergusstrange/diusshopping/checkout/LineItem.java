package com.github.fergusstrange.diusshopping.checkout;

import java.math.BigDecimal;
import java.util.Objects;

public class LineItem {

    private final InventoryItem inventoryItem;
    private final int count;
    private final BigDecimal discountValue;

    private LineItem(InventoryItem inventoryItem, int count, BigDecimal discountValue) {
        this.inventoryItem = inventoryItem;
        this.count = count;
        this.discountValue = discountValue;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public LineItemBuilder toBuilder() {
        return LineItemBuilder
                .aLineItem()
                .inventoryItem(inventoryItem)
                .count(count)
                .discountValue(discountValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return count == lineItem.count &&
                Objects.equals(inventoryItem, lineItem.inventoryItem) &&
                Objects.equals(discountValue, lineItem.discountValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryItem, count, discountValue);
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "inventoryItem=" + inventoryItem +
                ", count=" + count +
                ", discountValue=" + discountValue +
                '}';
    }


    public static final class LineItemBuilder {

        private InventoryItem inventoryItem;
        private int count;
        private BigDecimal discountValue;

        private LineItemBuilder() {
        }

        public static LineItemBuilder aLineItem() {
            return new LineItemBuilder();
        }

        public LineItemBuilder inventoryItem(InventoryItem inventoryItem) {
            this.inventoryItem = inventoryItem;
            return this;
        }

        public LineItemBuilder count(int count) {
            this.count = count;
            return this;
        }

        public LineItemBuilder discountValue(BigDecimal discountValue) {
            this.discountValue = discountValue;
            return this;
        }

        public LineItem build() {
            return new LineItem(inventoryItem, count, discountValue);
        }
    }
}
