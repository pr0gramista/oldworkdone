var uid = function () {
    return 'xxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {var r = Math.random()*16|0,v=c=='x'?r:r&0x3|0x8;return v.toString(16);});
}

var reward = function (experience, coins) {
  Materialize.toast('You have received ' + experience + ' exp and ' + coins + ' coins', 4000)
  userRepository.fetch();
}

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
    axios.put("/todo/" + todo.id, todo).then(function (r) {
      r.data.forEach(function (completation, index) {
        if(completation.task != null) { //TaskCompletation
          todo.tasks.find(function (task) {
            return task.content == completation.task.content && task.done && (task.rewarded == undefined || task.rewarded == false);
          }).rewarded = true;
          reward(completation.experience, completation.coins);
        }
        if(completation.todo != null) { //TodoCompletation
          todo.rewarded = true;
          reward(completation.experience, completation.coins);
        }
      });
    });
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
    user: userRepository.fetch(),
    selectedTag: ""
  },
  watch: {
  },
  created: function () {
    this.$on("selectTag", function (tag) {
      this.selectedTag = tag;
    });
  },
  computed: {
    filteredTodos: function () {
      var tag = this.selectedTag;
      if(this.todos != null && this.selectedTag.length > 0) {
        return this.todos.filter(function (todo) {
          return todo.tags.indexOf(tag) != -1;
        });
      }
      else {
        return this.todos;
      }
    },
    filteredHabits: function () {
      var tag = this.selectedTag;
      if(this.habits != null && this.selectedTag.length > 0) {
        return this.habits.filter(function (habit) {
          return habit.tags.indexOf(tag) != -1;
        });
      }
      else {
        return this.habits;
      }
    }
  },
  methods: {
    removeTagFilter: function () {
      this.selectedTag = "";
    },
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
