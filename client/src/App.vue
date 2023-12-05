<template>
  <div id="cart-app">
    <app-header />
    <main>
      <router-view />
    </main>
    <app-footer />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppFooter from './components/AppFooter.vue';
import ProductService from "./services/ProductService";


export default {
  components: {AppHeader, AppFooter},

  data(){
    return {
    }
  },

  methods: {
    getAllProducts() {
        this.isLoading=true
        ProductService
          .getAllProducts()
          .then((response) => {
            this.$store.state.products = response.data;
            this.isLoading=false;
          })
      },
  },

  created() {
    this.getAllProducts()
  }
  }

</script>

<style scoped>
#cart-app {
  height: 100vh;
  display: grid;
  grid-template-rows: auto 1fr 25px;
  grid-template-areas:
    "header"
    "main"
    "footer";
}

</style>