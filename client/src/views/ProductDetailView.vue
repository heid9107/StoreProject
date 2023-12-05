<template>
  <div>
    <div class="addToCart">
      <button v-if="isLoggedIn" v-on:click="addProductToCart">Add To Cart <font-awesome-icon class="paw" icon="fa-solid fa-paw" /></button>
    </div>
    <div class="currentProduct">
      <ProductDetails v-bind:productProp="currentProduct" />
    </div>
  </div>
</template>
  
<script>
import ProductDetails from '../components/ProductDetails.vue'
import CartService from '../services/CartService';

export default {
  components: {
    ProductDetails
  },
  computed: {
    currentProduct() {
      return this.$store.state.products.find(product => {
        if(product.productId == this.$route.params.n) {
          return product;
        } 
      })
    },

    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },  
  },

  methods: {
    addProductToCart() {
            CartService.addProductToCart(this.currentProduct);
            this.$store.commit('SET_MESSAGE', this.currentProduct.name +' Added To Cart');
            this.hideMessage();
        },

        hideMessage() {
            setTimeout(() => {
                this.$store.commit('SET_MESSAGE', '')
            }, 3000);
        }
  }
};
</script>

<style scoped>
  .addToCart {
    display: flex;
    justify-content: flex-end;
    cursor: pointer;
  }

  .currentProduct {
    display: flex;
    justify-content: center;
  }
  
  button {
    background-color: rgb(245, 200, 117);
    margin: 20px;
    cursor: pointer;
  }

  button:hover {
    box-shadow: 7px 7px 7px rgba(107, 1, 66, 0.6);
  }
  .paw {
    color: #3f87a6;
  }
</style>