<template>
  <div id="app">
    <header>
      <ul id="sidenav" class="side-nav fixed">
        <div class="logo">
          <img src="/images/workdone-logo-256.png"/>
        </div>
        <div class="profile">
          <profile :user="user"></profile>
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
          <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="#">Profile</a></li>
            <li><a href="#">Settings</a></li>
            <li><a @click="refreshTodos" href="#"><i class="material-icons">refresh</i></a></li>
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

export default {
  name: 'app',
  mounted: function () {
    /*eslint-disable */
    $('.button-collapse').sideNav({
      menuWidth: 240,
      draggable: true
    })
    /*eslint-enable */
  }
}
</script>

<style>
#app {
}
</style>
