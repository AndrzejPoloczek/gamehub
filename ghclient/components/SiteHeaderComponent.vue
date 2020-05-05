<template>
    <fv-header class="site-header-component">
      <div>
        <nuxt-link to="/">
          <img src="/images/gamehob-logo-white.png" width="32" />
          <span class="logo-title">GAME HUB</span>
        </nuxt-link>
      </div>
      <current-user-component />
    </fv-header>
</template>

<script>
    import CurrentUserComponent from "./CurrentUserComponent";
    export default {
      name: "SiteHeaderComponent",
      components: {CurrentUserComponent}
    }
</script>

<style scoped>
  .site-header-component {
    height: 0px;
  }

  .logo-title {
    margin-left: 12px;
    font-size: 32px;
  }
</style>
