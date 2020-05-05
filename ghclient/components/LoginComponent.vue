<template>
  <div class="fv-padding fv-text-left">
    <label class="fv-control-label fv-padding"> <i class="material-icons">person_add</i> Login </label>
    <div v-bind:class="{'fv-padding fv-bg-info': loginSuccess}">{{successMessage}}</div>
    <div v-bind:class="{'fv-padding fv-bg-danger': hasError}">{{errorMassage}}</div>
    <fvForm class="fv-row">
      <fvFormElement class="fv-col-md-6" label="Username">
        <fvInput v-model="userCreate.username" placeholder="Enter username" />
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Password">
        <fvInput type="password" v-model="userCreate.password" placeholder="Enter password" />
      </fvFormElement>
      <fvFormElement class="fv-col-offset-only-sm-6" >
        <fvButton v-on:click="login" class="fv-text-center fv-bg-primary">LogIn</fvButton>
      </fvFormElement>
    </fvForm>
  </div>
</template>

<script>
    import login from "../pages/login";
    import axios from "axios";

    export default {
        name: "LoginComponent",
        data() {
          return {
            userCreate: {
              username: 'patpat',
              password: '123123'
            },
            hasError: false,
            errorMassage: '',
            loginSuccess: false,
            successMessage: ''
          }
        },
        methods: {
          async login() {
            const params = new URLSearchParams();
            params.append('username', this.userCreate.username);
            params.append('password', this.userCreate.password);
            const headers = {
              'Content-Type': 'x-www-form-urlencoded',
              withCredentials: true,
            };
            try {
              await axios.post('http://localhost:8080/login', params, headers);
              this.hasError = false;
              this.errorMassage = '';
              this.loginSuccess = true;
              this.successMessage = 'You are logged in!';
              this.$router.push('/');
            } catch (e) {
              this.hasError = true;
              this.errorMassage = "Login failed, tray again.";
              this.loginSuccess = false;
              this.successMessage = '';
              console.log("Error: %o", e.response.data);
            }
          }
        }
    }
</script>

<style scoped>

</style>
