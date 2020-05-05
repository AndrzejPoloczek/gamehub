<template>
  <div class="content fv-padding">
    <LoginComponent />
    <div class="fv-padding fv-text-center fv-margin-top fv-margin-bottom">
      ------ OR ------
    </div>
    <UseCreateComponent />
  </div>
</template>

<script>
    import UseCreateComponent from "../components/UseCreateComponent";
    import LoginComponent from "../components/LoginComponent";
    import SiteHeaderComponent from "../components/SiteHeaderComponent";
    export default {
      name: "LoginPage",
      components: {SiteHeaderComponent, LoginComponent, UseCreateComponent}
    }
</script>

<style scoped>

</style>
