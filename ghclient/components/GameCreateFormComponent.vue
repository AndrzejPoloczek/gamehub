<template>
  <div class="game-create-form-ox3 fv-padding fv-text-left">
    <label class="fv-control-label fv-padding"> <i class="material-icons">person_add</i> {{name}} create </label>
    <fvForm class="fv-row">
      <fvFormElement class="fv-col-md-6" label="Username">
        <fvInput v-model="test" placeholder="Players" />
      </fvFormElement>
    </fvForm>
  </div>
</template>

<script>
    export default {
      name: "GameCreateFormComponent",
      props() {
        return {
          name: String,
          type: String,
          maxPlayers: Number,
          minPlayers: Number
        }
      },
      data() {
        return {
          create: {
            name: '',
            type: ''
          },
          test: ''
        }
      }
    }
</script>

<style scoped>

</style>
