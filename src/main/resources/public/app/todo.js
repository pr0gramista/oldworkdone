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
            hover: true
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
        <input v-if="is_editing" v-model="edit_title" class="generic" />
        <div v-for="(task, index) in todo.tasks" class="task">
          <input type="checkbox" v-model="task.done" class="filled-in" :id="'todo-' + todo.id + '-task-' + index"/>
          <label v-if="!is_editing" :for="'todo-' + todo.id + '-task-' + index">{{ task.content }}</label>
          <div class="inline-parent" v-if="is_editing">
            <input v-model="task.content" class="grow generic"/>
            <button class="clear" @click="deleteTask(index)" ><i class="material-icons">delete</i></button>
          </div>
        </div>
        <div v-if="is_editing" @keyup.enter="addNewTask" class="inline-parent">
          <input v-model="newTask" class="grow"/>
          <button @click="addNewTask" class="btn"><i class="material-icons">add</i></button>
        </div>

        <div class="card-settings" v-if="is_editing">
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
