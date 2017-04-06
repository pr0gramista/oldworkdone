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

var todoRepository = {
  fetch: function () {
    axios.get("/todo/").then(function (r) {
      app.todos = r.data;
    })
  },
  save: function (todo) {
    axios.put("/todo/" + todo.id, todo);
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
    todos: todoRepository.fetch(),
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
      var newTodo = {
        title: "Your title",
        color: "BLUE",
        expReward: "MEDIUM",
        coinReward: "MEDIUM",
        tasks: [],
        tags: []
      }
      axios.post("/todo/", newTodo).then(function (r) {
        newTodo.id = r.data; //id
        app.todos.push(newTodo);
      })
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
