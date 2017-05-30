<template>
  <div class="profile center" v-if="user != null">
    <img class="profile-picture center" :src="user.photoURL" />
    <h4>{{ user.displayName }}</h4>
    <i class="material-icons coins">attach_money</i> {{ displayCoins }} | <i class="material-icons exp">school</i> {{ displayLevel }}
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
    displayLevel: function () {
      return this.userData.level != null ? this.userData.level : 1
    },
    displayCoins: function () {
      return this.userData.coins != null ? this.userData.coins : 0
    },
    displayExperience: function () {
      return this.userData.experience != null ? this.userData.experience : 0
    },
    levelStyle: function () {
      var currentLevelExperience = Generator.generateLevelExperience(this.displayLevel)
      var deltaCurrentLevelExperience = this.displayExperience - currentLevelExperience
      var deltaNextLevelExperience = Generator.generateLevelExperience(this.displayLevel + 1) - currentLevelExperience
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
