<template>
  <div class="content fv-padding">
    <game-create-component />
  </div>
</template>

<script>
  import GameCreateComponent from "../components/GameCreateComponent";
  import SiteHeaderComponent from "../components/SiteHeaderComponent";
  export default {
    name: "index.vue",
    components: {SiteHeaderComponent, GameCreateComponent}
  }
</script>

<style scoped>

</style>
