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
  props: ['userData'],
  name: 'profile',
  data: function () {
    return {
      user: null
    }
  },
  computed: {
    levelStyle: function () {
      var currentLevelExperience = Generator.generateLevelExperience(this.user.level)
      var deltaCurrentLevelExperience = this.user.experience - currentLevelExperience
      var deltaNextLevelExperience = Generator.generateLevelExperience(this.user.level + 1) - currentLevelExperience
      return 'width: ' + deltaCurrentLevelExperience / deltaNextLevelExperience * 100 + '%;'
    }
  },
  mounted: function () {
    var profile = this
    firebase.auth().onAuthStateChanged(function (newUser) {
      if (newUser) {
        profile.user = newUser
      }
    })
  }
}
</script>

<style scoped>
</style>
