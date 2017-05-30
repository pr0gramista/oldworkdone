export default {
  uid: function () {
    return 'xxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0
      var v = c === 'x' ? r : r & 0x3 | 0x8
      return v.toString(16)
    })
  },
  /* Amount of experience required to get on given level fe. 100 -> 1.000.000 EXP */
  generateLevelExperience: function (level) {
    level = level - 1
    return level * level * 100 + level * 200 + 100
  },
  generateExperience: function () {
    return 100 + Math.floor(Math.random() * 50)
  },
  generateCoins: function () {
    return 100 + Math.floor(Math.random() * 50)
  }
}
