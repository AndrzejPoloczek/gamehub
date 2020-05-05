<template>
  <div class="game-create-component fv-padding fv-text-left">
    <div class="game-create-list" v-if="currentBind === undefined">
      <div class="game-create-desc fv-padding fv-text-left" v-for="item in gameTypes" :key="item.type">
        <div><h1>Available games to create</h1></div>
        <div class="game-create-item" ref="{{ item.name }}">
          <p class="game-header">{{item.name}}</p>
          <p class="game-description">{{item.description}}</p>
          <p class="game-attributes">
            <ul>
              <li>max players: <b>{{item.maxPlayers}}</b></li>
              <li>min players: <b>{{item.minPlayers}}</b></li>
            </ul>
          </p>
          <fvButton v-on:click="create(item.type)" class="game-create-button fv-text-center fv-bg-primary">Create</fvButton>
        </div>
      </div>
    </div>
    <div class="current-game-bind fv-padding fv-text-left" v-if="currentBind != undefined">
      <div><h1>Your current game</h1></div>
      <p class="game-header">{{currentBind.name}} <small>({{currentBind.type}})</small></p>
      <div v-if="isBindReady == false">
        <p>waiting for other players ...</p><br>
        <fvButton v-on:click="cancel" class="game-cancel-button fv-text-center fv-bg-danger">Cancel</fvButton>
        <fvButton v-on:click="checkStatus" class="game-check-button fv-text-center fv-bg-secondary">Check</fvButton>
      </div>
      <div v-if="isBindReady == true">
        <p>READY!!</p>
      </div>
    </div>
  </div>
</template>

<script>
    import axios from "axios";

    export default {
      name: "GameCreateComponent",
      components: {},
      data() {
        return {
          gameTypes: undefined,
          currentBind: undefined,
          isBindReady: false
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
          const res = await api.get('/game/bind/games');
          console.log("Found: %o", res.data);
          this.gameTypes = res.data;
        } catch (e) {
          console.log("Error: %o", e.response.data);
        }
      },
      methods: {
        async create(type) {

          const api = axios.create({
            baseURL: 'http://localhost:8080',
            withCredentials: true,
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            }
          });
          const params = {
            "players": 2,
            "type": type
          }

          try {
            const res = await api.post('/game/bind/create', params);
            console.log("Found: %o", res.data);
            console.log("- - - types: %o", this.gameTypes);

            for (var x in this.gameTypes) {
              if (this.gameTypes[x].type === type) {
                this.currentBind = this.gameTypes[x];
                this.$store.commit('setGameBind', res.data.guid);
              }
            }
          } catch (e) {
            console.log("Error: %o", e.response.data);
          }

        },
        cancel() {
          this.currentBind = undefined;
        },
        async checkStatus() {
          if (this.$store.state.gameBind == undefined) {
            return;
          }
          if (this.currentBind != undefined) {
            const api = axios.create({
              baseURL: 'http://localhost:8080',
              withCredentials: true,
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
              }
            });

            try {
              const res = await api.patch('/game/bind/update/player/status/' + this.$store.state.gameBind);
              console.log("Found: %o", res.data);
              this.$store.commit('setGameBind', undefined);
              // if (res.data.ready == true) {
              //   this.isBindReady = true;
              //   this.$store.commit('setGamePlay', res.data.playGuid);
              // } else if (res.data.canceled == true) {
              //   this.$store.state.gameBind == undefined;
              //   this.currentBind = undefined;
              // }
            } catch (e) {
              console.log("Error: %o", e);
            }
          }
        }
      }
    }
</script>

<style scoped>
  .game-create-desc {
    background-color: #7f828b;
  }

  .game-header {
    margin-bottom: 12px;
    font-size: 24px;
    border-bottom: 1px solid #000;
  }

  .game-description {
    font-style: italic;
    margin-bottom: 12px;
    padding-bottom: 12px;
    border-bottom: 1px solid #000;
  }

  .game-create-item {
    position: relative;
  }

  .game-create-button {
    position: absolute;
    right: 0;
    bottom: 0;
  }

  .current-game-bind {
    position: relative;
    background-color: #7f828b;
  }
</style>
