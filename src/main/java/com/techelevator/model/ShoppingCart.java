package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.List;

@JsonPropertyOrder({"cartItemsList", "subTotalForUser", "salesTax", "totalForUser"})
public class ShoppingCart {
    List<CartItems> cartItemsList;
    private BigDecimal salesTax;
    private BigDecimal total;

    public List<CartItems> getCartItemsList() {
        return cartItemsList;
    }

    public void setCartItemsList(List<CartItems> cartItemsList) {
        this.cartItemsList = cartItemsList;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public BigDecimal getSubTotalForUser() {
        BigDecimal subtotal = new BigDecimal("0.00");
        BigDecimal total = new BigDecimal("0.00");

        for (CartItems cartItems : cartItemsList) {
            Product product = cartItems.getProduct();
            BigDecimal itemPrice = product.getPrice().multiply(new BigDecimal(cartItems.getQuantity()));
            subtotal = subtotal.add(itemPrice);
        }
        return subtotal;
    }

    public BigDecimal getTotalForUser() {
        BigDecimal total = new BigDecimal("0.00");

        total = getSubTotalForUser().add(salesTax);
        return total;
    }
}
