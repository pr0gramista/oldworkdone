<template>
  <section>
    <div class="main-box centered">
      <img src="../assets/workdone-logo-256.png" alt="Logo"/>
      <h1>Workdone</h1>
      <div class="motto">Make your life a beautiful game and get motivated</div>
      <button @click="loginWithGoogle"><img src="../assets/btn-google-sigin-light.png" alt="Sign-In"/></button>
    </div>
  </section>
</template>

<script>
import firebase from 'firebase'

var provider = new firebase.auth.GoogleAuthProvider()

export default {
  name: 'landing',
  methods: {
    loginWithGoogle: function () {
      const landing = this
      firebase.auth().signInWithPopup(provider).then(function (result) {
        landing.$router.push('/work')
      }).catch(function (error) {
        console.log('Got error on login with Google: ' + error)
      })
    }
  },
  mounted: function () {
    const landing = this
    firebase.auth().onAuthStateChanged(function (newUser) {
      if (newUser) {
        landing.$router.push('/work')
      }
    })
  }
}
</script>

<style scoped>
section {
    font-family: 'Roboto', sans-serif;
    padding: 0;
    margin: 0;
    height: 100vh;
    background: url('../assets/background.jpg');
    background-position: center;
    background-size: cover;
}

button {
  padding: 0;
  outline: 0;
  border: 0;
  background: none;
  margin: 10px;
}

h1 {
    font-family: 'Roboto Condensed', sans-serif;
    font-size: 80px;
    margin: 0;
}

.main-box {
    position: relative;
    top: 200px;
}

.centered {
    text-align: center;
    margin-left: auto;
    margin-right: auto;
}

.motto {
    font-size: 20px;
}
</style>
