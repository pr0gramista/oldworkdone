<template>
  <div>
    <div class="row" v-if="selectedTag.length > 0">
      <div class="big chip">
        {{ selectedTag }}
        <i class="close material-icons" @click="removeTagFilter()">close</i>
      </div>
    </div>
    <div class="grid">
      <habit v-on:selectTag="setTag" v-for="(habit, index) in filteredHabits" :habit="habit" :index="index" v-on:saveHabit="saveHabit"></habit>
      <todo v-on:selectTag="setTag" v-for="(todo, index) in filteredTodos" :todo="todo" :index="index" v-on:saveTodo="saveTodo"></todo>
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
import Todo from '@/components/Todo'
import Habit from '@/components/Habit'
import firebase from 'firebase'
import _ from 'lodash'

export default {
  name: 'dashboard',
  components: {
    Todo, Habit
  },
  created: function () {
    var dashboard = this
    if (dashboard.$store.getters.uuid != null) {
      dashboard.updateReferences()
    } else {
      this.$store.watch(
        function () {
          return dashboard.$store.getters.uuid
        },
        function (uuid) {
          console.log(uuid)
          dashboard.updateReferences()
        }
      )
    }
  },
  data: function () {
    return {
      selectedTag: '',
      habits: [],
      todos: []
    }
  },
  computed: {
    filteredTodos: function () {
      var tag = this.selectedTag
      if (this.todos !== null && this.selectedTag.length > 0) {
        return this.todos.filter(function (todo) {
          return todo.tags != null && todo.tags.indexOf(tag) !== -1
        })
      } else {
        return this.todos
      }
    },
    filteredHabits: function () {
      var tag = this.selectedTag
      if (this.habits !== null && this.selectedTag.length > 0) {
        return this.habits.filter(function (habit) {
          return habit.tags != null && habit.tags.indexOf(tag) !== -1
        })
      } else {
        return this.habits
      }
    }
  },
  mounted: function () {
    /*eslint-disable */
    window.$grid = $('.grid').packery({
      itemSelector: '.grid-item',
      gutter: 0,
      columnWidth: 300
    })
    /*eslint-enable */
  },
  methods: {
    updateReferences: function () {
      this.$bindAsArray('habits', firebase.database().ref(this.$store.getters.uuid + '/habits/'))
      this.$bindAsArray('todos', firebase.database().ref(this.$store.getters.uuid + '/todos/'))
    },
    removeTagFilter: function () {
      this.selectedTag = ''
    },
    setTag: function (tag) {
      this.selectedTag = tag
      Vue.nextTick(function () {
        /*eslint-disable */
        window.$grid.packery()
        /*eslint-enable */
      })
    },
    addNewHabit: function () {
      var newHabit = {
        text: 'Your text',
        color: 'BLUE',
        value: 'NORMAL',
        tags: []
      }
      this.$firebaseRefs.habits.push(newHabit)
    },
    addNewTodo: function () {
      var newTodo = {
        title: 'Your title',
        color: 'BLUE',
        value: 'NORMAL',
        tasks: [],
        tags: []
      }
      this.$firebaseRefs.todos.push(newTodo)
    },
    addNewChallenge: function () {
      console.log('Add new challenge')
    },
    saveTodo: function (todo) {
      const key = todo['.key']

      var todoToSave = _.clone(todo)
      delete todoToSave['.key']
      this.$firebaseRefs.todos.child(key).set(todoToSave)
    },
    saveHabit: function (habit) {
      const key = habit['.key']

      var habitToSave = _.clone(habit)
      delete habitToSave['.key']
      this.$firebaseRefs.habits.child(key).set(habitToSave)
    }
  }
}
</script>

<style scoped>
</style>
