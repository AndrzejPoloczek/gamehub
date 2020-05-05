<template>
  <div class="current-user-component">
    <span v-if="!authorized">
      -
    </span>
    <span v-if="authorized">
      Hello {{getDisplayName}}
    </span>
  </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "CurrentUserComponent",
        data() {
          return {
            authorized: false
          }
        },
        computed: {
            getUsername: function() {
              return this.$store.state.username;
            },
            getDisplayName: function() {
              return this.$store.state.displayName;
            },
            getGameBind: function() {
              return this.$store.state.gameBind;
            },
            getGamePlay: function() {
              return this.$store.state.gamePlay;
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
            params: {}
          });
          try {
            const res = await api.get('/user/get');
            this.authorized = true;
            this.$store.commit('setUsername', res.data.username);
            this.$store.commit('setDisplayName', res.data.displayName);
          } catch (e) {
            this.authorized = false;
            this.$store.commit('setUsername', undefined);
            this.$store.commit('setDisplayName', undefined);
            this.$router.push('/login');
          }
        }
    }
</script>

<style scoped>

</style>
