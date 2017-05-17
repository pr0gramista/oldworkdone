<template>
  <div class="profile center">
    <img class="profile-picture center" :src="user.photo" />
    <h4>{{ user.name }}</h4>
    <i class="material-icons coins">attach_money</i> {{ user.coins }} | <i class="material-icons exp">school</i> {{ user.level }}
    <div class="progress">
      <div class="determinate" :style="levelStyle"></div>
    </div>
  </div>
</template>

<script>
import Generator from '/Generator'

export default {
  name: 'habit',
  props: ['user'],
  computed: {
    levelStyle: function () {
      var currentLevelExperience = Generator.levelGenerator(this.user.level)
      var deltaCurrentLevelExperience = this.user.experience - currentLevelExperience
      var deltaNextLevelExperience = Generator.levelGenerator(this.user.level + 1) - currentLevelExperience
      return 'width: ' + deltaCurrentLevelExperience / deltaNextLevelExperience * 100 + '%;'
    }
  }
}
</script>

<style scoped>
</style>
