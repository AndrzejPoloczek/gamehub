<template>
  <div class="fv-padding fv-text-left">
    <label class="fv-control-label fv-padding"> <i class="material-icons">person_add</i> Login </label>
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
              username: '',
              password: ''
            }
          }
        },
        methods: {
          async login() {
            const config = {
              headers: {
                'Accept': "application/json",
                'data': this.userCreate
              }
            }

            try {
              const res = await axios.post('http://localhost:8080/login', config);
              console.log("Found: %o", res);
            } catch (e) {
              console.log("Error: %o", e.response);
            }
          }
        }
    }
</script>

<style scoped>

</style>
