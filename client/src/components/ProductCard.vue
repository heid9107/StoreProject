<template>
    <article class="product-card">
        <div class="sku">{{ product.productSKU }}</div>
        <div class="price">${{ product.price }}</div>
        <router-link v-bind:to="{ name: 'product', params: { n: this.product.productId } }" 
            class="product-name">{{ product.name }}</router-link>
        <img class="product-image" src="https://via.placeholder.com/350x250.jpg" />
        <div class="cart">
            <font-awesome-icon v-if="isLoggedIn" v-on:click="addProductToCart" class="paw" icon="fa-solid fa-paw"
                title="Add item to cart" />
        </div>
    </article>
</template>
  
<script>
import CartService from '../services/CartService';

export default {
    props: ['product'],

    methods: {
        addProductToCart() {
            CartService.addProductToCart(this.product);
            this.$store.commit('SET_MESSAGE', this.product.name +' Added To Cart');
            this.hideMessage();
        },

        hideMessage() {
            setTimeout(() => {
                this.$store.commit('SET_MESSAGE', '')
            }, 3000);
        }
    },

    computed: {
        isLoggedIn() {
            return this.$store.state.token.length > 0;
        },
    }
};
</script>
  
<style scoped>
.product-card {
    border: 2px solid black;
    padding: 15px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas:
        'sku price'
        'product-name product-name'
        'image image'
        '. cart-icon';
    font-weight: 700;
    font-size: 30px;
    width: 400px;
    border-radius: 10%;
    background-color: rgb(248, 250, 219);
}

.product-card:hover {
    box-shadow: 7px 7px 7px rgba(107, 1, 66, 0.6);
}

.sku {
    grid-area: sku;
}

.price {
    grid-area: price;
    display: flex;
    justify-content: right;
}

.product-name {
    grid-area: product-name;
    padding-bottom: 15px;
}

.product-image {
    grid-area: image;
}

img {
    border-radius: 20%;
}

.cart {
    grid-area: cart-icon;
    display: flex;
    justify-content: flex-end;
}

.paw {
    color: #3f87a6;
    font-size: 30px;
    cursor: pointer;
}

.paw:hover {
    color: rgb(255, 98, 41);
}
</style>