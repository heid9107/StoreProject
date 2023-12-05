package com.techelevator.model;

import javax.validation.constraints.Positive;

public class CartItems {

    private int cartItemId;
    private int userId;
    @Positive
    private int productId;
    @Positive
    private int quantity;
    private Product product;

    public CartItems() { }

    public CartItems(int cartItemId, int userId, int productId, int quantity, Product product) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.product = product;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }
}
