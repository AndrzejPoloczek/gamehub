<template>
  <div>
    <span v-if="!authorized">
      You are not logged in
    </span>
    <span v-if="authorized">
      Loffed as {{displayName}} ({{username}})
    </span>
  </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "CurrentUserComponent",
        data() {
          return {
            username: '',
            displayName: '',
            bindingGuid: '',
            playingGuid: '',
            authorized: false
          }
        },
        async created() {
          const config = {
            headers: {
              'Accept': "application/json"
            }
          }

          try {
            const res = await axios.get('http://localhost:8080/user/get', config);
            console.log("Found: %o", res);
          } catch (e) {
            console.log("Error: %o", e.response.data);
            this.authorized = false;
          }
        }
    }
</script>

<style scoped>

</style>
