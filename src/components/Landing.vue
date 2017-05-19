<template>
  <div>
    This is landing page!
    <button @click="loginWithGoogle">Login</button>
  </div>
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
  }
}
</script>

<style scoped>
</style>
