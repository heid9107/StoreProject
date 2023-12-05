<template>
  <div id="register">
    <form v-on:submit.prevent="register">
      <h1>Create Account</h1>
      <div id="fields">
        <div class="Wrapper">
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
        <div class="Wrapper">
          <label for="name">Name: </label>
          <input
            type="text"
            id="name"
            placeholder="Name"
            v-model="user.name"
            required
          />
        </div>  
        <div class="Wrapper"> 
          <label for="password">Password: </label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            v-model="user.password"
            required
          />
        </div>   
        <div class="Wrapper">
          <label for="confirmPassword">Confirm password: </label>
          <input
            type="password"
            id="confirmPassword"
            placeholder="Confirm Password"
            v-model="user.confirmPassword"
            required
          />
        </div>
        <div class="Wrapper">
          <label for="address">Address: </label>
          <input
            type="text"
            id="address"
            placeholder="Address"
            v-model="user.address"
          />
        </div>
        <div class="Wrapper">
          <label for="city">City: </label>
          <input type="text" id="city" placeholder="City" v-model="user.city" />
        </div>
        <div class="Wrapper">
          <label for="state">State: </label>
          <input
            type="text"
            id="state"
            placeholder="State"
            v-model="user.stateCode"
            maxlength="2"
            required
          />
        </div>
        <div class="Wrapper">
          <label for="zip">ZIP: </label>
          <input
            type="number"
            id="zip"
            placeholder="ZIP"
            v-model="user.zip"
            required
            minlength="5"
            maxlength="5"
          />
        </div>  
        <div></div>
        <div>
          <button type="submit">Create Account <font-awesome-icon class="paw" icon="fa-solid fa-paw" /></button>
        </div>
      </div>
      <hr />
      Have an account?
      <router-link v-bind:to="{ name: 'login' }">Sign in!</router-link>
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
        name: "",
        password: "",
        confirmPassword: "",
        address: "",
        city: "",
        stateCode: "",
        zip: "",
        role: "user",
      },
    };
  },
  methods: {
    error(msg) {
      alert(msg);
    },
    success(msg) {
      alert(msg);
    },
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.error("Password & Confirm Password do not match");
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.success("Thank you for registering, please sign in.");
              this.$router.push({
                path: "/login",
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            if (!response) {
              this.error(error);
            } else if (response.status === 400) {
              if (response.data.errors) {
                // Show the validation errors
                let msg = "Validation error: ";
                for (let err of response.data.errors) {
                  msg += `'${err.field}':${err.defaultMessage}. `;
                }
                this.error(msg);
              } else {
                this.error(response.data.message);
              }
            } else {
              this.error(response.data.message);
            }
          });
      }
    },
  },
};
</script>

<style scoped>

.Wrapper {
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
}

.paw {
  color: #3f87a6;
}

</style>
