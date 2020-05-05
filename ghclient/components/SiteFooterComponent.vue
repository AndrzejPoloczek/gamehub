<template>
  <fv-footer class="site-footer-component">
    <fv-form class="fv-row">
      <fvFormElement class="fv-col-md-6" label="About">
        <ul>
          <li>
            <small>Game Hub application by Andrzej Poloczek</small>
          </li>
          <li>
            <small>Contact: <a href="mailto:andrzej.poloczek@gmail.com">andrzej.poloczek@gmail.com</a></small>
          </li>
        </ul>
      </fvFormElement>
      <fvFormElement class="fv-col-md-6" label="Licence">
        <small class="licence">
          You may copy, distribute and modify the software as long as you track changes/dates in source files. Any modifications to or software including (via compiler) GPL-licensed code must also be made available under the GPL along with build & install instructions.
        </small>
      </fvFormElement>
    </fv-form>
  </fv-footer>
</template>

<script>
    export default {
        name: "SiteFooterComponent"
    }
</script>

<style scoped>
  .site-footer-component .licence {
    text-align: justify;
    width: 60%;
  }
</style>
