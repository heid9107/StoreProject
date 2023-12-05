package com.techelevator.controller;


import com.techelevator.dao.CartItemsDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItems;
import com.techelevator.model.ShoppingCart;
import com.techelevator.service.CartItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/cart")
public class ShoppingCartController {

    private CartItemsDao cartItemsDao;
    private CartItemsService cartItemsService;
    private ProductDao productDao;
    private UserDao userDao;

    public ShoppingCartController(CartItemsDao cartItemsDao, CartItemsService cartItemsService) {
        this.cartItemsDao = cartItemsDao;
        this.cartItemsService = cartItemsService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ShoppingCart list(Principal principal) {
        return cartItemsService.getUserCart(principal);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/items", method = RequestMethod.POST)
    public CartItems addCartItem(@Valid @RequestBody CartItems newCartItems, Principal principal){
        CartItems cartItems = null;

        try {
            cartItems = cartItemsService.addCartItem(newCartItems, principal);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return cartItems;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = "items/{itemId}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable int itemId, Principal principal) {
          cartItemsService.deleteCartItem(itemId, principal);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public void delete(Principal principal) {
            cartItemsService.deleteCart(principal);
    }

}
