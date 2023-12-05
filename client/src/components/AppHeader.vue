<template>
  <header id="app-header">
    <div id="puppy">
        <img id="logo-image" class="logo" src="@/assets/ShihtzuLogo.avif" alt="Shih Tzu Image"/>
    </div>
    <app-nav />
  </header>
</template>

<script>
import AppNav from './AppNav.vue';

  export default {
    name: 'AppHeader',

    components: {AppNav},
};
</script>

<style scoped>
  #app-header {
    grid-area: header;
    border-bottom: 2px solid black;
    background: linear-gradient(0.25turn, #3f87a6, white, #f69d3c);
  }

  #puppy {
    display: flex;
    justify-content: center;
    box-shadow: 0 0 8px 8px white inset;
  }

  img {
    height: 200px;
    width: 250px;
    border-radius: 50%;
  }
</style>