import axios from 'axios';

export default {
    getCart() {
        return axios.get('/cart')
    },

    addProductToCart(product) {
        const cartItem = {
            productId: product.productId,
            quantity: 1
        }
        return axios.post('/cart/items', cartItem)
    },

    removeProductFromCart(id) {
        return axios.delete(`/cart/items/${id}`)
    },

    clearCart() {
        return axios.delete('/cart')
    }
}