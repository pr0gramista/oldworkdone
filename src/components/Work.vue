<template>
  <div>
    <header>
      <ul id="sidenav" class="side-nav fixed">
        <div class="logo">
          <img src="../assets/workdone-logo-256.png"/>
        </div>
        <div class="profile">
          <profile :userData="userData"></profile>
        </div>
        <li><a href="#!">Habits</a></li>
        <li><a href="#!">Tasks</a></li>
        <li><a href="#!">Profile</a></li>
        <li><a href="#!">Settings</a></li>
      </ul>
      <nav>
        <div class="nav-wrapper">
          <a href="#" class="brand-logo center">Workdone</a>
          <a href="#" data-activates="sidenav" class="button-collapse"><i class="material-icons">menu</i></a>
          <ul id="nav-mobile" class="right">
            <li><a href="#">Make profile picture here!</a></li>
          </ul>
        </div>
      </nav>
    </header>
    <div>
      <main>
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script>
import Profile from '@/components/Profile'

export default {
  name: 'work',
  data: function () {
    return {
      userData: {
        coins: 0,
        level: 1
      }
    }
  },
  mounted: function () {
    /*eslint-disable */
    $('.button-collapse').sideNav({
      menuWidth: 240,
      draggable: true
    })
    /*eslint-enable */
  },
  components: {
    Profile
  }
}
</script>

<style scoped>
</style>
