<template>
    <div class="shopping-cart">
        <h1>Shopping Cart</h1>
        <button v-on:click="clearCart()">Clear Cart <font-awesome-icon class="paw" icon="fa-solid fa-paw" title="Clear Cart" /></button>
    </div>
    <table>
        <thead>
            <tr>
                <th>Qty</th>
                <th>Product</th>
                <th>Price</th>
                <th>Amount</th>
                <th>Remove</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in cartItems.cartItemsList" v-bind:key="item.cartItemId">
                <td>{{ item.quantity }}</td>
                <td>{{ item.product.name }}</td>
                <td>${{ item.product.price }}</td>
                <td>${{ item.quantity * item.product.price }}</td>
                <td><font-awesome-icon class="paw" icon="fa-solid fa-paw" v-on:click="removeProductFromCart(item.cartItemId)" title="Remove item to cart" /></td>
            </tr>
            <tr class="itemInformation">
                <td></td>
                <td>Item subtotal:</td>
                <td></td>
                <td>${{cartItems.subTotalForUser}}</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td>Tax:</td>
                <td></td>
                <td>${{cartItems.salesTax}}</td>
            </tr>
            <tr>
                <td></td>
                <td>Total:</td>
                <td></td>
                <td>${{cartItems.totalForUser}}</td>
            </tr>
        </tbody>
    </table>
</template>
  
<script>
import CartService from '../services/CartService.js';

export default {
    components: {},
    data() {
        return {
            cartItems: {},
        }
    },
    methods: {
        getCart() {
            CartService
                .getCart()
                .then(response => {
                    this.cartItems = response.data;
                })
                .catch(error => {
                    console.error(error);
                });

        },

        removeProductFromCart(cartItemId) {
            CartService
                .removeProductFromCart(cartItemId)
                .then(response => {
                    if (response.status === 204) {
                        this.getCart();
                    }
                })
                .catch(error => {
                    console.error(error);
                });
        },

        clearCart() {
            CartService
                .clearCart()
                .then(response => {
                    if (response.status === 204) {
                        this.getCart();
                    }
                })
                .catch(error => {
                    console.error(error);
                });
                
        }
    },
    created() {
        this.getCart();
    }
};
</script>

<style scoped>
.shopping-cart {
    display: flex;
    flex-direction: row;
    margin: 20px;
    justify-content: space-between;
}

.shopping-cart h1 {
    font-size: 30px;
}

button {
    background-color: rgb(245, 200, 117);
    margin: 20px;
    cursor: pointer;
}

table {
    border: 1px solid black;
    min-width: 400px;
    margin: 20px;
    text-align: center;
    border-collapse: collapse;
}

thead {
    background-color: rgb(161, 85, 136);
    color: white;
    border-bottom: 2px solid #000; 
}

.itemInformation td {
    border-top: 2px solid black; 
}

.paw {
    color: #3f87a6;
    font-size: 18px;
    cursor: pointer;
}

.paw:hover {
    color: rgb(255, 98, 41);
}
</style>