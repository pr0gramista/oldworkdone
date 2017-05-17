<template>
  <div>
    <div class="row" v-if="selectedTag.length > 0">
      <div class="big chip">
        {{ selectedTag }}
        <i class="close material-icons" @click="removeTagFilter()">close</i>
      </div>
    </div>
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
</template>

<script>
import Vue from 'vue'
import '@/components/Profile'
import '@/components/Todo'
import '@/components/Habit'

export default {
  name: 'dashboard',
  data: function () {
    return {
      habits: [], // habitRepository.fetch(this),
      todos: [], // todoRepository.fetch(this),
      selectedTag: ''
    }
  },
  computed: {
    filteredTodos: function () {
      var tag = this.selectedTag
      if (this.todos !== null && this.selectedTag.length > 0) {
        return this.todos.filter(function (todo) {
          return todo.tags.indexOf(tag) !== -1
        })
      } else {
        return this.todos
      }
    },
    filteredHabits: function () {
      var tag = this.selectedTag
      if (this.habits !== null && this.selectedTag.length > 0) {
        return this.habits.filter(function (habit) {
          return habit.tags.indexOf(tag) !== -1
        })
      } else {
        return this.habits
      }
    }
  },
  methods: {
    removeTagFilter: function () {
      this.selectedTag = ''
    },
    setTag: function (tag) {
      this.selectedTag = tag
      Vue.nextTick(function () {
        // $grid.packery()
      })
    },
    addNewHabit: function () {
      /* var component = this
      var newHabit = {
        title: 'Your title',
        color: 'BLUE',
        expReward: 'MEDIUM',
        coinReward: 'MEDIUM',
        tags: []
      } */
      /* axios.post("/habit/", newHabit).then(function (r) {
        newHabit.id = r.data //id
        component.habits.push(newHabit)
      }) */
    },
    addNewTodo: function () {
      /* var component = this
      var newTodo = {
        title: 'Your title',
        color: 'BLUE',
        expReward: 'MEDIUM',
        coinReward: 'MEDIUM',
        tasks: [],
        tags: []
      } */
      /* axios.post("/todo/", newTodo).then(function (r) {
        newTodo.id = r.data; //id
        component.todos.push(newTodo)
      }) */
    },
    addNewChallenge: function () {
      console.log('Add new challenge')
    },
    refreshTodos: function () {
      // habitRepository.fetch()
    }
  }
}
</script>

<style scoped>
.grid-item { width: 25%; }
.grid-item--width2 { width: 50%; }

</style>
