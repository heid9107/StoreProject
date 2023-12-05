<template>
    <header>
      <nav id="navBar">
        <div id="HomeAndCart">
          <RouterLink v-bind:to="{name: 'home'}">Home</RouterLink>
          <RouterLink v-bind:to="{name: 'cart'}">Cart</RouterLink>
        </div>
          <li id="message" v-bind:class="{'message': $store.state.message}">
            {{ $store.state.message }}</li>
        <div>
          <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
          Logout
        </router-link>
        <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
        </div>
      </nav>   
    </header>
  </template>
  
  <script>

  </script>
  
  <style scoped>
  #navBar {
    border-top: 2px solid black;
    display: flex;
    flex-grow: 1;
    justify-content: space-between;
    background-color: rgb(243, 206, 206);
    opacity: 90%;
    margin: 0%;
    padding-left: 5px;
    padding-right: 5px;
  }

  #HomeAndCart {
    display: flex;
    justify-content: space-between;
    width: 10%;
  }

  #message {
    display: flex;
    justify-content: center;
  }
  </style>