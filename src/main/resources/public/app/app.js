var habitRepository = {
  fetch: function () {
    axios.get("/habit/").then(function (r) {
      app.habits = r.data;
    })
  },
  save: function (habit) {
    axios.put("/habit/" + habit.id, habit);
  }
}

var userRepository = {
  fetch: function() {
    axios.get("/user/").then(function (r) {
      app.user = r.data;
    }).catch(function (e) {
      console.log(e);
    })
  }
}

var app = new Vue({
  el: '#app',
  data: {
    habits: habitRepository.fetch(),
    newHabit: {
      text: "przykladowy tekst",
      color: "RED",
      expReward: "HIGH",
      coinReward: "HIGH",
      tags: []
    },
    user: userRepository.fetch()
  },
  watch: {
  },
  methods: {
    addNewHabit: function () {
      var newHabit = this.newHabit;
      axios.post("/habit/", newHabit).then(function (r) {
        newHabit.id = r.data; //id
        app.habits.push(newHabit);
        newHabit.id = null;
      })
    },
    refreshTodos: function () {
      habitRepository.fetch();
    }
  }
})

setInterval(app.refreshTodos, 5000);
