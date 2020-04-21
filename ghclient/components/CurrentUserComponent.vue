<template>
  <div>
    <span v-if="!authorized">
      You are not logged in
    </span>
    <span v-if="authorized">
      Logged as {{displayName}} ({{username}})
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
          const api = axios.create({
            baseURL: 'http://localhost:8080',
            withCredentials: true,
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            params: {
            }
          });

          try {
            const res = await api.get('/user/get');
            console.log("Found: %o", res.data);
            this.authorized = true;
            this.username = res.data.username;
            this.displayName = res.data.displayName;
          } catch (e) {
            console.log("Error: %o", e.response.data);
            this.authorized = false;
          }
        }
    }
</script>

<style scoped>

</style>
