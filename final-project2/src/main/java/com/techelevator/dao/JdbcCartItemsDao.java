package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItems;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartItemsDao implements CartItemsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemsDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CartItems> getItemsInCart(int userId) {
        List<CartItems> shoppingCartList = new ArrayList<>();

        try {
            String sql = "SELECT cart_item_id, user_id, product_id, quantity FROM cart_item WHERE user_id = ?";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                CartItems shoppingCart = mapRowToShoppingCart(results);
                shoppingCartList.add(shoppingCart);
            }
        } catch (
                CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return shoppingCartList;
    }

    public CartItems getItemsById(int id) {
        String sql = "SELECT cart_item_id, user_id, product_id, quantity FROM cart_item WHERE cart_item_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        CartItems cartItems = null;
        if (results.next()) {
            cartItems = mapRowToShoppingCart(results);
        }
        return cartItems;
    }

    @Override
    public CartItems getItemsByUserIdAndProductId(int userId, int productId) {
        String sql = "SELECT cart_item_id, user_id, product_id, quantity FROM cart_item WHERE user_id = ? AND product_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, productId);

        CartItems cartItems = null;
        if (results.next()) {
            cartItems = mapRowToShoppingCart(results);
        }
        return cartItems;
    }

    @Override
    public CartItems addCartItem(CartItems cartItems) {
        CartItems newCartItems = null;
        String sql = "INSERT INTO cart_item (user_id, product_id, quantity) VALUES (?, ?, ?) RETURNING cart_item_id;";

        try {
            int newCartItemId = jdbcTemplate.queryForObject(sql, int.class, cartItems.getUserId(),
                    cartItems.getProductId(), cartItems.getQuantity());
            newCartItems = getItemsById(newCartItemId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation For Post Method", e);
        }
        return newCartItems;
    }

    @Override
    public CartItems updateCartItem(CartItems cartItems){
        CartItems updatedCartItems = null;
        String sql = "Update cart_item SET quantity = ? WHERE cart_item_id = ?;";

        try{
            int rowsUpdated = jdbcTemplate.update(sql, cartItems.getQuantity(), cartItems.getCartItemId());
                if(rowsUpdated == 0){
                    throw new DaoException("Nothing updated");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation for Update method", e);
        } updatedCartItems = getItemsById(cartItems.getCartItemId());
        return updatedCartItems;
    }

    @Override
    public void deleteCartItem(int cartItem) {
        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?;";

        try {
            jdbcTemplate.update(sql, cartItem);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public void deleteCartByUserId(int userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?;";

        try {
            jdbcTemplate.update(sql, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

//        CartItems items = null;
//        for (CartItems shoppingCart : shoppingCartList) {
//            if (shoppingCart.getCartItemId() == cartItemId) {
//                items = shoppingCart;
//                break;
//            }
//        }
//        if (items != null) {
//            shoppingCartList.remove(items);
//            return 1; // number of rows affected
//        } else {
//            return 0; // number of rows affected
//        }
    }

    private CartItems mapRowToShoppingCart(SqlRowSet result) {
        CartItems shoppingCart = new CartItems();
        shoppingCart.setCartItemId(result.getInt("cart_item_id"));
        shoppingCart.setUserId(result.getInt("user_id"));
        shoppingCart.setProductId(result.getInt("product_id"));
        shoppingCart.setQuantity(result.getInt("quantity"));
        return shoppingCart;
    }
}
