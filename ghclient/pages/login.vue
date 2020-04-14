<template>
  <fvMain>
    <div class="content fv-padding">

      <CurrentUserComponent />
      <LoginComponent />

      <div class="fv-padding fv-text-center fv-margin-top fv-margin-bottom">
       ------ OR ------
      </div>

      <UseCreateComponent />
    </div>
  </fvMain>

</template>

<script>
    import UseCreateComponent from "../components/UserLoginComponent";
    import LoginComponent from "../components/LoginComponent";
    import CurrentUserComponent from "../components/CurrentUserComponent";
    export default {
        name: "LoginPage",
      components: {CurrentUserComponent, LoginComponent, UseCreateComponent}
    }
</script>

<style scoped>

</style>
