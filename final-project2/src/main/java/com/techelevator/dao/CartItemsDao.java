package com.techelevator.dao;

import com.techelevator.model.CartItems;

import java.util.List;

public interface CartItemsDao {

    List<CartItems> getItemsInCart(int userId);

    CartItems updateCartItem(CartItems cartItems);

    void deleteCartItem(int cartItem);

    void deleteCartByUserId(int userId);

    CartItems addCartItem(CartItems newCartItems);

    CartItems getItemsById(int cartItem);

    CartItems getItemsByUserIdAndProductId(int userId, int productId);
}
