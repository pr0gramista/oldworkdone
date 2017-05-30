import firebase from 'firebase'
import store from '@/store'
import Generator from '@/Generator'

const defaultExperience = 0
const defaultLevel = 1
const defaultCoins = 10

export default {
  addExperience: function (amount) {
    const uid = store.state.user.uid
    firebase.database().ref(uid + '/user/').once('value').then(function (snapshot) {
      let experience, level
      if (snapshot.val() == null) {
        experience = defaultExperience
        level = defaultLevel
      } else {
        experience = snapshot.val().experience != null ? snapshot.val().experience : defaultExperience
        level = snapshot.val().level != null ? snapshot.val().level : defaultLevel
      }

      let newExperience = experience + amount

      let update = {}

      while (newExperience > Generator.generateLevelExperience(level + 1)) {
        update['level'] = level + 1
        level = level + 1
      }

      update['experience'] = newExperience
      firebase.database().ref(uid + '/user/').update(update)
    })
  },
  addCoins: function (amount) {
    const uid = store.state.user.uid
    firebase.database().ref(uid + '/user/').once('value').then(function (snapshot) {
      let coins
      if (snapshot.val() == null) {
        coins = defaultCoins
      } else {
        coins = snapshot.val().coins != null ? snapshot.val().coins : defaultCoins
      }

      var newCoins = coins + amount

      firebase.database().ref(uid + '/user/').update({
        'coins': newCoins
      })
    })
  }
}
