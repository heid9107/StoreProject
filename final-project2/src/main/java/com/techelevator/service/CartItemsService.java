package com.techelevator.service;

import com.techelevator.dao.CartItemsDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;

@Component
public class CartItemsService {
    RestTemplate restTemplate = new RestTemplate();

    private ProductDao productDao;
    private UserDao userDao;
    private CartItemsDao cartItemsDao;
    private SalesTaxService salesTaxService;

    public CartItemsService(ProductDao productDao, UserDao userDao, CartItemsDao cartItemsDao,
                            SalesTaxService salesTaxService) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.cartItemsDao = cartItemsDao;
        this.salesTaxService = salesTaxService;
    }

    public ShoppingCart getUserCart(Principal principal){
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = userDao.getUserByUsername(principal.getName());
        List<Product> productList = productDao.getProductsByUserId(user.getId());
        shoppingCart.setCartItemsList(cartItemsDao.getItemsInCart(user.getId()));
        for(CartItems cartItems : shoppingCart.getCartItemsList()){
            cartItems.setProduct(findProduct(productList, cartItems.getProductId()));
        }
        BigDecimal tax = salesTaxService.getTaxRate(user.getStateCode());
        BigDecimal taxCalculated = tax.divide(BigDecimal.valueOf(100));
        BigDecimal subtotal = shoppingCart.getSubTotalForUser();
        BigDecimal finalTax = taxCalculated.multiply(subtotal).setScale(2, RoundingMode.UP);
        shoppingCart.setSalesTax(finalTax);
        BigDecimal total = shoppingCart.getTotalForUser();

        return shoppingCart;
    }

    private Product findProduct(List<Product> products, int productId){
        for(Product item: products){
            if(item.getProductId() == productId){
                return item;
            }
        }return null;
    }

    public CartItems addCartItem(CartItems newCartItems, Principal principal) {
        CartItems cartItems = null;

        int userId = getUser(principal).getId();
        newCartItems.setUserId(userId);

        CartItems currentItem = cartItemsDao.getItemsByUserIdAndProductId(newCartItems.getUserId(), newCartItems.getProductId());
        if (currentItem != null) {
            currentItem.setQuantity(currentItem.getQuantity() + newCartItems.getQuantity());
            cartItemsDao.updateCartItem(currentItem);
            return currentItem;
        } else {
            return cartItemsDao.addCartItem(newCartItems);
        }
    }

    public void deleteCartItem(int cartItem, Principal principal) {
        CartItems cartItems = cartItemsDao.getItemsById(cartItem);

        if(cartItems == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        User user = getUser(principal);

        if(cartItems.getUserId() != user.getId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Authorized");
        }
        cartItemsDao.deleteCartItem(cartItem);
    }

    public void deleteCart(Principal principal) {
        int userId = getUser(principal).getId();

        cartItemsDao.deleteCartByUserId(userId);
    }

    private User getUser(Principal principal) {
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        return user;
    }
}
