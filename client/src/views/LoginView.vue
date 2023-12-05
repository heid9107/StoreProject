<template>
  <div id="login">
    <form v-on:submit.prevent="login">
      <h1>Please Sign In</h1>
      <div id="fields">
        <div class="usernameWrapper">
          <label for="username">Username: </label>
          <input
            type="text"
            id="username"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
          />
        </div>
        <div class="passwordWrapper">
          <label for="password">Password: </label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            v-model="user.password"
            required
          />
        </div>
        <div><button type="submit">Sign in <font-awesome-icon class="paw" icon="fa-solid fa-paw" /></button></div>
      </div>
      <hr/>
      Need an account? <router-link v-bind:to="{ name: 'register' }">Register!</router-link>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;
          if (!response) {
            alert(error);
          } else if (response.status === 401) {
            alert("Invalid username and password!");
          } else {
            alert(response.message);
          }
        });
    },
  },
};
</script>

<style scoped>
  .passwordWrapper {
    margin-top: 15px;
    margin-bottom: 15px;
  }

  #fields {
    padding: 10px;
    border: solid gray 1px;
    width: 200px;
    margin: 20px;
  }

  button {
    background-color: rgb(245, 200, 117);
    cursor: pointer;
  }
  .paw {
    color: #3f87a6;
  }
</style>
