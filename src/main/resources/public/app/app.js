var levelGenerator = function(l) {
  l = l - 1;
  return l*l*100 + l*200 + 100;
}

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
    user: userRepository.fetch()
  },
  watch: {
  },
  methods: {
    addNewHabit: function () {
      var newHabit = {
        text: "Your title",
        color: "RED",
        expReward: "MEDIUM",
        coinReward: "MEDIUM",
        tags: []
      }
      axios.post("/habit/", newHabit).then(function (r) {
        newHabit.id = r.data; //id
        app.habits.push(newHabit);
      })
    },
    addNewTodo: function () {
      console.log("Add new todo");
    },
    addNewChallenge: function () {
      console.log("Add new challenge");
    },
    refreshTodos: function () {
      habitRepository.fetch();
    }
  }
})

setInterval(app.refreshTodos, 5000);
