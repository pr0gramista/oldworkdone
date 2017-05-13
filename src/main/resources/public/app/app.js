var router = new VueRouter({
    mode: 'history',
    base: window.location.href,
    routes: []
});


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
  fetch: function (component) {
    axios.get("/habit/").then(function (r) {
      component.habits = r.data;
    })
  },
  save: function (habit) {
    axios.put("/habit/" + habit.id, habit);
  }
}

var todoRepository = {
  fetch: function (component) {
    axios.get("/todo/").then(function (r) {
      component.todos = r.data;
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

Vue.component('dashboard', {
  data: function () {
    return {
      habits: habitRepository.fetch(this),
      todos: todoRepository.fetch(this),
      selectedTag: ""
    };
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
    setTag: function (tag) {
      this.selectedTag = tag;
      Vue.nextTick(function () {
        $grid.packery()
      })
    },
    addNewHabit: function () {
      var component = this;
      var newHabit = {
        text: "Your title",
        color: "RED",
        expReward: "MEDIUM",
        coinReward: "MEDIUM",
        tags: []
      }
      axios.post("/habit/", newHabit).then(function (r) {
        newHabit.id = r.data; //id
        component.habits.push(newHabit);
      })
    },
    addNewTodo: function () {
      var component = this;
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
        component.todos.push(newTodo);
      })
    },
    addNewChallenge: function () {
      console.log("Add new challenge");
    },
    refreshTodos: function () {
      habitRepository.fetch();
    }
  },
  template:
    `
    <div>
      <div class="row" v-if="selectedTag.length > 0">
         <div class="big chip">
           {{ selectedTag }}
           <i class="close material-icons" @click="removeTagFilter()">close</i>
         </div>
       </div>
       <style>
       .grid-item { width: 25%; }
       .grid-item--width2 { width: 50%; }

       </style>
       <div class="grid">
           <habit v-on:selectTag="setTag" v-for="(habit, index) in filteredHabits" :habit="habit" :index="index" :key="habit.id"></habit>
           <todo v-on:selectTag="setTag" v-for="(todo, index) in filteredTodos" :todo="todo" :index="index" :key="todo.id"></todo>
       </div>
       <div class="fixed-action-btn">
           <button class="btn-floating btn-large waves-effect waves-light amber">
               <i class="large material-icons">add</i>
           </button>
           <ul>
               <li>
                   <button @click="addNewHabit" class="btn-floating blue darken-1"><i
                           class="material-icons">thumb_up</i></button>
               </li>
               <li>
                   <button @click="addNewTodo" class="btn-floating orange darken-1"><i class="material-icons">list</i>
                   </button>
               </li>
               <li>
                   <button @click="addNewChallenge" class="btn-floating teal darken-1"><i class="material-icons">linear_scale</i>
                   </button>
               </li>
           </ul>
       </div>
     </div>
    `
});

var app = new Vue({
  router,
  el: '#app',
  data: {
    user: userRepository.fetch()
  },
  methods: {
    refreshTodos: function () {
      habitRepository.fetch();
    }
  }
})

//setInterval(app.refreshTodos, 5000);

var $grid = $('.grid').packery({
  itemSelector: '.grid-item',
  gutter: 0,
  columnWidth: 300
});

$grid.find('.grid-item').each( function( i, gridItem ) {
  var draggie = new Draggabilly( gridItem );
  $grid.packery( 'bindDraggabillyEvents', draggie );
});
