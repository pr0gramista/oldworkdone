export default {
  uid: function () {
    return 'xxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0
      var v = c === 'x' ? r : r & 0x3 | 0x8
      return v.toString(16)
    })
  },
  levelGenerator: function (l) {
    l = l - 1
    return l * l * 100
  }
}
