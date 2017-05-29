<template>
  <div class="profile center" v-if="user != null">
    <img class="profile-picture center" :src="user.photoURL" />
    <h4>{{ user.displayName }}</h4>
    <i class="material-icons coins">attach_money</i> {{ userData.coins }} | <i class="material-icons exp">school</i> {{ userData.level }}
    <div class="progress">
      <div class="determinate" :style="levelStyle"></div>
    </div>
  </div>
</template>

<script>
import Generator from '@/Generator'
import firebase from 'firebase'

export default {
  name: 'profile',
  data: function () {
    return {
      userData: {}
    }
  },
  computed: {
    user: function () {
      return this.$store.state.user
    },
    levelStyle: function () {
      var currentLevelExperience = Generator.generateLevelExperience(this.user.level)
      var deltaCurrentLevelExperience = this.user.experience - currentLevelExperience
      var deltaNextLevelExperience = Generator.generateLevelExperience(this.user.level + 1) - currentLevelExperience
      return 'width: ' + deltaCurrentLevelExperience / deltaNextLevelExperience * 100 + '%;'
    }
  },
  created: function () {
    var profile = this
    if (profile.$store.getters.uuid != null) {
      profile.updateReferences()
    } else {
      this.$store.watch(
        function () {
          return profile.$store.getters.uuid
        },
        function (uuid) {
          profile.updateReferences()
        }
      )
    }
  },
  methods: {
    updateReferences: function () {
      this.$bindAsObject('userData', firebase.database().ref(this.$store.getters.uuid + '/user/'))
    }
  }
}
</script>

<style scoped>
</style>
