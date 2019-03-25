package com.github.fergusstrange.diusshopping;

import com.github.fergusstrange.diusshopping.checkout.Checkout;
import com.github.fergusstrange.diusshopping.checkout.CheckoutProvider;

public class Application {

    public static void main(String... args) {
        Checkout checkout = CheckoutProvider.newCheckout();

        checkout.scan("mbp");
        checkout.scan("vga");

        System.out.println("$" + checkout.total());
    }
}
