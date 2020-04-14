<template>
  <div class="fv-padding fv-text-left">
    <label class="fv-control-label fv-padding"> <i class="material-icons">person_add</i> Create New User </label>
    <fvForm class="fv-row">
      <fvFormElement class="fv-col-md-6" label="Username">
        <fvInput v-model="userCreate.username" placeholder="Enter username" />
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Display name">
        <fvInput v-model="userCreate.displayName" placeholder="Enter display name" />
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Password">
        <fvInput type="password" v-model="userCreate.password" placeholder="Enter password" />
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Confirm password">
        <fvInput type="password" v-model="userCreate.passwordRepeat" placeholder="Confirm password" />
      </fvFormElement>
      <fvFormElement class="fv-col-offset-only-sm-6" >
        <fvButton class="fv-text-center fv-bg-primary">Create</fvButton>
      </fvFormElement>
    </fvForm>
  </div>
</template>

<script>
    export default {
        name: "UseCreateComponent",
        data() {
          return {
            userCreate: {
              username: '',
              displayName: '',
              password: '',
              passwordRepeat: ''
            }
          }
        }
    }
</script>

<style scoped>

</style>
