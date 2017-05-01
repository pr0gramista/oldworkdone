Vue.component('todo', {
  props: ['todo', 'index'],
  watch: {
    todo: {
      handler: function (todo) {
        todoRepository.save(todo);
      },
      deep: true
    }
  },
  data: function() {
    return {
      unique: uid(),
      newTag: "",
      newTask: ""
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
  mounted: function() {
    var unique = this.unique;
    Vue.nextTick(function () {
      $('.dropdown-button').dropdown({
          hover: true
        }
      );
    })
  },
  methods: {
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
    },
    addTag: function () {
      if(this.newTag.length > 0) {
        this.todo.tags.push(this.newTag);
        this.newTag = '';
      }
    },
    deleteTag: function (index) {
      this.todo.tags.splice(index, 1);
    },
    selectTag: function (index) {
      this.$emit("selectTag", this.todo.tags[index]);
    }
  },
  template:
  `<div class="todo col s12 m6 l4 xl3">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content hover-visible-parent">
        <input v-model="todo.title" class="generic" />
        <ul class="todo-task-list">
          <task v-for="(task, index) in todo.tasks" v-on:deleteThisTask="deleteTask(index)" :task="task" :index="index"></task>
        </ul>

        <div @keyup.enter="addNewTask" class="new-task inline-parent hover-visible">
          <input v-model="newTask" class="grow"/>
          <button @click="addNewTask" class="btn"><i class="material-icons">add</i></button>
        </div>

        <div class="card-settings hover-visible">
          <a class='dropdown-button' href='#' :data-activates="'dropdown-' + unique">
            <div class="color-icon" v-bind:class="colorDropdownClass"></div>
          </a>
          <ul :id="'dropdown-' + unique" class='dropdown-content'>
            <li><a href="#" @click="setColor('RED')"><div class="color-icon red"></div></a></li>
            <li><a href="#" @click="setColor('BLUE')"><div class="color-icon blue"></div></a></li>
            <li><a href="#" @click="setColor('GREEN')"><div class="color-icon green"></div></a></li>
            <li><a href="#" @click="setColor('WHITE')"><div class="color-icon white"></div></a></li>
            <li><a href="#" @click="setColor('GRAY')"><div class="color-icon gray"></div></a></li>
          </ul>
        </div>
        <div :id="'chips-' + unique" class="chips">
          <tag v-for="(text, index) in todo.tags
            "class="chip" @click="selectTag(index)" :text="text" :index="index"
            v-on:deleteThisTag="deleteTag(index)">
          </tag>
          <input @keyup.enter="addTag" v-model="newTag" placeholder="New tag"/>
        </div>
      </div>
    </div>
  </div>`
})
