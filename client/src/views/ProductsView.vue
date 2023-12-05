<template>
  <div class="home">
    <div id="heading-line">
    <!-- <h2>Loading spinner demonstration</h2>
    <p>
      This is a demonstration of how you can show or hide a "spinner" icon to
      let the user know something is happening. Before calling an API, you'd set
      the data property <code>isLoading</code> to <code>true</code>. When the
      call completes, set it to <code>false</code>.
    </p>
    <p>
      For this demonstration, clicking the checkbox below sets
      <code>isLoading</code> to <code>true</code>, so it simulates what the user
      would see when your app is accessing an API.
    </p>
    <input type="checkbox" name="loading" id="loading" v-model="isLoading" /> Is
    Loading -->
    </div>
      <p id="login-message" v-if="!isLoggedIn">
        Welcome! You may browse anonymously as much as you wish,<br />
        but you must
        <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add
        items to your shopping cart.
      </p>
    <nav id="productHeader">
      <div id="header">
        <h1>
            Products
            <loading-spinner id="spinner" v-bind:spin="isLoading" />
        </h1>
        <div id="iconsAndSearch">
          <font-awesome-icon
            v-bind:class="{ 'view-icon': true, active: cardView }"
            v-on:click="cardView = true"
            icon="fa-solid fa-grip"
            title="View tiles"
          />
          <font-awesome-icon
            v-bind:class="{ 'view-icon': true, active: !cardView }"
            v-on:click="cardView = false"
            icon="fa-solid fa-table"
            title="View table"
          />
          <div id="search" class="input">
                <input type="text" id="search-bar" placeholder="Search..." v-on:keyup="filteredList"  v-model="searchTerm" />
                <font-awesome-icon class="searchIcon" icon="fa-solid fa-magnifying-glass" v-on:click="tryEmit" />
          </div>
        </div>
      </div>
        <p> 
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
          sed do eiusmod tempor incididunt ut labore et dolore magna 
          aliqua. Ut enim ad minim veniam, quis nostrud exercitation 
          ullamco laboris nisi ut aliquip ex ea commodo consequat.
        </p>
    </nav>
  </div>
  <section id="product-cards">
    <product-card v-for="eachProduct in filteredProducts" v-bind:key="eachProduct.productId" 
                  v-bind:product="eachProduct"/> 
  </section>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import ProductCard from "../components/ProductCard.vue";
import ProductService from "../services/ProductService";

export default {
  components: {
    LoadingSpinner, ProductCard
  },

  data() {
    return {
      isLoading: false,
      cardView: true,
      filteredProducts: [],
      filter: '',
      searchTerm: "",
      lastSearchTerm: "",
    };
  },

  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
    
    search() {
      return this.$store.state.products.filter(product => product.name == this.filter)
    }
  },

  methods: {
      filteredList(searchTerm) {
        this.filter = searchTerm;
        this.searchProducts();
      },

      searchProducts() {
        this.isLoading=true
        ProductService
          .searchProducts(this.searchTerm)
          .then((response) => {
            this.filteredProducts = response.data;
            this.isLoading=false;
          })
      },

      tryEmit(){
        if (this.searchTerm !== this.lastSearchTerm) {
          this.$emit("keyup", this.searchTerm);
          this.lastSearchTerm = this.searchTerm;
        }
      },
    },

    created() {
      this.searchProducts()
    }
  
};
</script>

<style scoped>
h1 {
  font-size: 30px;
}

  #spinner {
  color: green;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: lightgreen;
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color: blue;
  background-color: rgba(255, 255, 255, 0.7);
}

.input {
  grid-area: search;
  height: 30px;
  padding: 2px;
  border-radius: 50px;
  border: 1px solid #756e6e;
  background-color: white;
  display:flex;
  justify-content: flex-end;
}

input {
  border:none; /* removed input block */
  height:100%;
  font-size: 15px;
  background-color: transparent;
  display: flex;
  flex-grow: 1;
} 

input:focus {   /*removes the black box when typing in it */
  outline: none; 
} 

.input:hover{       /*search bar hover*/
  box-shadow: 1px 1px 8px 1px #696666;
}

.searchIcon {         /*size of magnifying glass*/
  font-size: 18px;
}

#header {
  display: flex;
}

#iconsAndSearch {
  display: flex;
  flex-grow: 1;
  justify-content: flex-end;
  align-items: center;
}

#product-cards {
  display: flex;
  padding: 20px;
  flex-wrap: wrap;
  gap: 20px 20px;
}
</style>