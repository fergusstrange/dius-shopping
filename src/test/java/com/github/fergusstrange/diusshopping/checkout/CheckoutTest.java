package com.github.fergusstrange.diusshopping.checkout;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class CheckoutTest {

    @Test
    public void handlesIncorrectSkuScan_NoItemsMatch_ReturningZero() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("lolz");
        checkout.scan("cat");

        assertThat(checkout.total()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void handlesIncorrectSkuScan_OneItemsMatch_ReturningZero() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("lolz");
        checkout.scan("cat");
        checkout.scan("mbp");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("1399.99"));
    }

    @Test
    public void shouldApplyNoDiscounts() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("ipd");
        checkout.scan("mbp");
        checkout.scan("atv");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("2059.48"));
    }

    @Test
    public void shouldApplySomeForThePriceOfFewerOnAppleTV() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("vga");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("249.00"));
    }

    @Test
    public void shouldApplySomeForThePriceOfFewerManyTimes() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("vga");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("468.00"));
    }

    @Test
    public void shouldApplySomeForThePriceOfFewerManyTimes_RoundingToWholeDivisibleQuantity() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("atv");
        checkout.scan("vga");
        checkout.scan("atv");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("577.50"));
    }

    @Test
    public void shouldApplyBulkDiscountForIPad() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("atv");
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout.scan("atv");
        checkout.scan("ipd");
        checkout.scan("ipd");
        checkout.scan("ipd");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("2718.95"));
    }

    @Test
    public void shouldApplyBundleDiscountForVGA() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("mbp");
        checkout.scan("vga");
        checkout.scan("ipd");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("1949.98"));
    }

    @Test
    public void onlyAppliesBundleDiscountThreeTimes() {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("mbp");
        checkout.scan("vga");
        checkout.scan("vga");
        checkout.scan("vga");
        checkout.scan("mbp");
        checkout.scan("vga");
        checkout.scan("mbp");
        checkout.scan("ipd");

        assertThat(checkout.total()).isEqualTo(new BigDecimal("4779.96"));
    }
}