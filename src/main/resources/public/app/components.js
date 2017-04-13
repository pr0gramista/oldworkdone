Vue.component('profile', {
  props: ['user'],
  computed: {
    levelStyle: function() {
      var currentLevelExperience = levelGenerator(this.user.level)
      var deltaCurrentLevelExperience = this.user.experience - currentLevelExperience;
      var deltaNextLevelExperience = levelGenerator(this.user.level + 1) - currentLevelExperience;
      return "width: " + deltaCurrentLevelExperience/deltaNextLevelExperience * 100 + "%;";
    }
  },
  template:
  `
  <div class="profile center">
    <img class="profile-picture center" :src="user.photo" />
    <h4>{{ user.name }}</h4>
    <i class="material-icons coins">attach_money</i> {{ user.coins }} | <i class="material-icons exp">school</i> {{ user.level }}
    <div class="progress">
      <div class="determinate" :style="levelStyle"></div>
    </div>
  </div>
  `
})

Vue.component('todo', {
  props: ['todo', 'index'],
  data: function () {
    return {
      is_editing: false,
      edit_title: '',
      newTask: ''
    }
  },
  watch: {
    todo: {
      handler: function (todo) {
        todoRepository.save(todo);
      },
      deep: true
    }
  },
  computed: {
    cardClass: function () {
      return "color-" + this.todo.color.toLowerCase();
    },
    colorDropdownClass: function () {
      return this.todo.color.toLowerCase();
    }
  },
  methods: {
    startEdit: function () {
      this.is_editing = true;
      this.edit_title = this.todo.title;

      Vue.nextTick(function () {
        $('.dropdown-button').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrainWidth: false, // Does not change width of dropdown to that of the activator
            hover: true, // Activate on hover
            gutter: 0, // Spacing from edge
            belowOrigin: false, // Displays dropdown below the button
            alignment: 'left', // Displays dropdown with edge aligned to the left of button
            stopPropagation: false // Stops event propagation
          }
        );
      })
    },
    endEdit: function () {
      this.is_editing = false;
      this.todo.title = this.edit_title;
    },
    setColor: function (color) {
      this.todo.color = color;
    },
    addNewTask: function () {
      if(this.newTask.length > 0) {
        this.todo.tasks.push({
          content: this.newTask,
          done: false
        });
        this.newTask = '';
      }
    },
    deleteTask: function (index) {
      this.todo.tasks.splice(index, 1);
    }
  },
  template:
  `<div class="todo col s12 m6 l4">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content visible-parent-hover">
        <i v-if="!is_editing" @click="startEdit" class="material-icons right visible-child-hover">edit</i>
        <i v-if="is_editing" @click="endEdit" class="material-icons right">done</i>
        <span v-if="!is_editing">{{ todo.title }}</span>
        <input v-if="is_editing" v-model="edit_title" />
        <div v-for="(task, index) in todo.tasks">
          <input type="checkbox" v-model="task.done" class="filled-in" :id="'todo-' + todo.id + '-task-' + index"/>
          <label v-if="!is_editing" :for="'todo-' + todo.id + '-task-' + index">{{ task.content }}</label>
          <div class="inline-parent" v-if="is_editing">
            <input v-model="task.content" class="grow"/>
            <button class="clear" @click="deleteTask(index)" ><i class="material-icons">delete</i></button>
          </div>
        </div>
        <div v-if="is_editing" @keyup.enter="addNewTask" class="inline-parent">
          <input v-model="newTask" class="grow"/>
          <button @click="addNewTask" class="btn"><i class="material-icons">add</i></button>
        </div>
        <div v-if="is_editing">
          <a class='dropdown-button' href='#' :data-activates="'dropdown-' + index">
            <div class="color-icon" v-bind:class="colorDropdownClass"></div>
          </a>
          <ul :id="'dropdown-' + index" class='dropdown-content'>
            <li><a href="#" @click="setColor('RED')"><div class="color-icon red"></div></a></li>
            <li><a href="#" @click="setColor('BLUE')"><div class="color-icon blue"></div></a></li>
            <li><a href="#" @click="setColor('GREEN')"><div class="color-icon green"></div></a></li>
            <li><a href="#" @click="setColor('WHITE')"><div class="color-icon white"></div></a></li>
            <li><a href="#" @click="setColor('GRAY')"><div class="color-icon gray"></div></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>`
})

Vue.component('habit', {
  props: ['habit', 'index'],
  data: function () {
    return {
      is_editing: false,
      edit_text: ''
    }
  },
  watch: {
    habit: {
      handler: function (habit) {
        habitRepository.save(habit);
      },
      deep: true
    }
  },
  computed: {
    cardClass: function () {
      return "color-" + this.habit.color.toLowerCase();
    }
  },
  methods: {
    startEdit: function () {
      this.is_editing = true;
      this.edit_text = this.habit.text;

      Vue.nextTick(function () {
        $('.dropdown-button').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrainWidth: false, // Does not change width of dropdown to that of the activator
            hover: true, // Activate on hover
            gutter: 0, // Spacing from edge
            belowOrigin: false, // Displays dropdown below the button
            alignment: 'left', // Displays dropdown with edge aligned to the left of button
            stopPropagation: false // Stops event propagation
          }
        );
      })
    },
    endEdit: function () {
      this.is_editing = false;
      this.habit.text = this.edit_text;
    },
    setColor: function (color) {
      this.habit.color = color;
    },
    done: function() {
      axios.get("/habit/" + this.habit.id + "/done/").then(function (r) {
        var exp = r.data.experience
        var coins = r.data.coins
        Materialize.toast('You have received ' + exp + ' exp and ' + coins + ' coins', 4000)
        userRepository.fetch();
      });
    }
  },
  template:
  `<div class="habit col s12 m6 l4">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content visible-parent-hover">
        <i v-if="!is_editing" @click="startEdit" class="material-icons right visible-child-hover">edit</i>
        <i v-if="is_editing" @click="endEdit" class="material-icons right">done</i>
        <p v-if="!is_editing">{{ habit.text }}</p>
        <textarea v-if="is_editing" v-model="edit_text" />
        <div v-if="is_editing">
          <a class='dropdown-button' href='#' :data-activates="'dropdown-' + index">
            <div class="color-icon" v-bind:class="colorDropdownClass"></div>
          </a>
          <ul :id="'dropdown-' + index" class='dropdown-content'>
            <li><a href="#" @click="setColor('RED')"><div class="color-icon red"></div></a></li>
            <li><a href="#" @click="setColor('BLUE')"><div class="color-icon blue"></div></a></li>
            <li><a href="#" @click="setColor('GREEN')"><div class="color-icon green"></div></a></li>
            <li><a href="#" @click="setColor('WHITE')"><div class="color-icon white"></div></a></li>
            <li><a href="#" @click="setColor('GRAY')"><div class="color-icon gray"></div></a></li>
          </ul>
        </div>
      </div>
      <div class="card-action">
        <button @click="done" class="waves-effect waves-light btn">Done</button>
      </div>
    </div>
  </div>`
})
