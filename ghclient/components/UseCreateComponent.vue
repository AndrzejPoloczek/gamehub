<template>
  <div class="fv-padding fv-text-left">
    <label v-show="showForm" class="fv-control-label fv-padding"> <i class="material-icons">person_add</i> Create New User </label>
    <div v-bind:class="{'fv-padding fv-bg-info': createSuccess}">{{successMessage}}</div>
    <div v-bind:class="{'fv-padding fv-bg-danger': hasError}">{{errorMassage}}</div>
    <fvForm class="fv-row" v-show="showForm">
      <fvFormElement class="fv-col-md-6" label="Username">
        <fvInput v-model="userCreate.username" placeholder="Enter username" />
        <p v-show="errors.username" class="fv-padding fv-bg-danger">{{errors.username}}</p>
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Display name">
        <fvInput v-model="userCreate.displayName" placeholder="Enter display name" />
        <p v-show="errors.displayName" class="fv-padding fv-bg-danger">{{errors.displayName}}</p>
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Password">
        <fvInput type="password" v-model="userCreate.password" placeholder="Enter password" />
        <p v-show="errors.password" class="fv-padding fv-bg-danger">{{errors.password}}</p>
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Confirm password">
        <fvInput type="password" v-model="userCreate.passwordRepeat" placeholder="Confirm password" />
        <p v-show="errors.passwordRepeat" class="fv-padding fv-bg-danger">{{errors.passwordRepeat}}</p>
      </fvFormElement>
      <fvFormElement class="fv-col-offset-only-sm-6" >
        <fvButton v-on:click="create" class="fv-text-center fv-bg-primary">Create</fvButton>
      </fvFormElement>
    </fvForm>
  </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "UseCreateComponent",
        data() {
          return {
            showForm: true,
            userCreate: {
              username: '',
              displayName: '',
              password: '',
              passwordRepeat: ''
            },
            errors: {},
            hasError: false,
            errorMassage: '',
            createSuccess: false,
            successMessage: ''
          }
        },
        methods: {
          async create() {

            const params = {
              username: this.userCreate.username,
              displayName: this.userCreate.displayName,
              password: this.userCreate.password,
              passwordRepeat: this.userCreate.passwordRepeat
            };

            try {
              const res = await axios.post('http://localhost:8080/user/create', params);
              this.hasError = false;
              this.errorMassage = '';
              this.successMessage = 'Account has been created';
              this.showForm = false;
              console.log("Created account: %o", res);
            } catch (e) {
              this.hasError = true;
              this.errorMassage = "Error while creating user";
              this.showForm = true;
              this.successMessage = '';
              if (e.response.data !== undefined && e.response.data.errors !== undefined) {
                this.errors = {};
                let serverErrors = e.response.data.errors;
                for (var x in serverErrors) {
                  this.errors[serverErrors[x].fieldname] = serverErrors[x].message;
                }
              }
              console.log("Error: %o", e.response);
            }
          }
        }
    }
</script>

<style scoped>

</style>
