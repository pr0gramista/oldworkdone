<template>
  <div>
    <button @click="loginWithGoogle">Login</button>
  </div>
</template>

<script>
import firebase from 'firebase'

var provider = new firebase.auth.GoogleAuthProvider()

export default {
  name: 'front',
  methods: {
    loginWithGoogle: function () {
      const front = this
      firebase.auth().signInWithPopup(provider).then(function (result) {
        front.$router.push('/dashboard')
      }).catch(function (error) {
        console.log('Got error on login with Google: ' + error)
      })
    }
  }
}
</script>

<style scoped>
</style>
