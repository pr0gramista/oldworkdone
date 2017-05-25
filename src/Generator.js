export default {
  uid: function () {
    return 'xxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0
      var v = c === 'x' ? r : r & 0x3 | 0x8
      return v.toString(16)
    })
  },
  generateLevelExperience: function (level) {
    return level * level * 100 + level * 200 + 100
  },
  generateExperience: function () {
    return 100 + Math.floor(Math.random() * 50)
  },
  generateCoins: function () {
    return 100 + Math.floor(Math.random() * 50)
  }
}
