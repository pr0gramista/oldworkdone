<template>
  <div class="todo grid-item">
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
            <li><a @click="setColor('RED')"><div class="color-icon red"></div></a></li>
            <li><a @click="setColor('BLUE')"><div class="color-icon blue"></div></a></li>
            <li><a @click="setColor('GREEN')"><div class="color-icon green"></div></a></li>
            <li><a @click="setColor('WHITE')"><div class="color-icon white"></div></a></li>
            <li><a @click="setColor('GRAY')"><div class="color-icon gray"></div></a></li>
          </ul>
          <button @click="changeValue()" class="circle-button"><i class="material-icons">{{ valueIcon }}</i></button>
        </div>
        <div :id="'chips-' + unique" class="chips">
          <tag v-for="(text, index) in todo.tags
          "class="chip" @click="selectTag(index)" :text="text" :index="index"
          v-on:deleteThisTag="deleteTag(index)" v-on:selectTag="setTag">
          </tag>
          <input @keyup.enter="addTag" v-model="newTag" placeholder="New tag"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Tag from '@/components/Tag'
import Task from '@/components/Task'
import Generator from '@/Generator'
import Vue from 'vue'
import Draggabilly from 'draggabilly'
import _ from 'lodash'
import executor from '@/executor'

const valueTypes = [
  'NORMAL', 'HIGHER', 'HIGH'
]
const valueTypesIcons = [
  'star_border', 'star_half', 'star'
]
const valueFactor = [
  1, 1.5, 2
]
const baseRewardFactor = 0.25
const perTaskRewardFactor = 0.75

export default {
  name: 'todo',
  props: ['todo', 'index', 'grid'],
  components: {
    Task, Tag
  },
  watch: {
    todo: {
      handler: _.debounce(function (todo) {
        // No more tasks left to do
        if (this.todo.tasks != null && this.todo.tasks.length > 0) {
          let tasksDone = this.todo.tasks.filter(task => task.done == null || !task.done)
          if (tasksDone.length === 0 && !this.todo.done) {
            this.todo.done = true
            this.done()
          }
        }

        this.$emit('saveTodo', this.todo)
      }, 500),
      deep: true
    }
  },
  data: function () {
    return {
      unique: Generator.uid(),
      newTag: '',
      newTask: ''
    }
  },
  computed: {
    valueIcon: function () {
      let index = valueTypes.indexOf(this.todo.value)
      if (index === -1) {
        return valueTypesIcons[0]
      } else {
        return valueTypesIcons[index]
      }
    },
    valueFactor: function () {
      let index = valueTypes.indexOf(this.todo.value)
      if (index === -1) {
        return valueFactor[0]
      } else {
        return valueFactor[index]
      }
    },
    cardClass: function () {
      return 'color-' + this.todo.color.toLowerCase()
    },
    colorDropdownClass: function () {
      return this.todo.color.toLowerCase()
    }
  },
  mounted: function () {
    var element = this.$el
    Vue.nextTick(function () {
      /*eslint-disable */
      $('.dropdown-button').dropdown({
        hover: true
      })

      window.$grid.prepend(element).packery('prepended', element)
      var draggie = new Draggabilly(element)
      window.$grid.packery('bindDraggabillyEvents', draggie)
      /*eslint-enable */
    })
  },
  methods: {
    setColor: function (color) {
      this.todo.color = color
    },
    addNewTask: function () {
      if (this.newTask.length > 0) {
        // Create task list if it doesn't exist
        if (this.todo.tasks === undefined) {
          this.$set(this.todo, 'tasks', [])
        }

        this.todo.tasks.push({
          content: this.newTask,
          done: false
        })
        this.newTask = ''
      }
    },
    changeValue: function () {
      let index = valueTypes.indexOf(this.todo.value)
      if (index === -1) {
        this.todo.value = valueTypes[0]
      } else {
        index = index + 1 >= valueTypes.length ? 0 : index + 1
        this.todo.value = valueTypes[index]
      }
    },
    deleteTask: function (index) {
      this.todo.tasks.splice(index, 1)
    },
    addTag: function () {
      if (this.newTag.length > 0) {
        // Create tag list if it doesn't exist
        if (this.todo.tags === undefined) {
          this.$set(this.todo, 'tags', [])
        }

        this.todo.tags.push(this.newTag)
        this.newTag = ''
      }
    },
    done: function () {
      const experience = Generator.generateExperience() * this.valueFactor * baseRewardFactor + Generator.generateExperience() * this.todo.tasks.length * perTaskRewardFactor
      const coins = Generator.generateCoins() * this.valueFactor * baseRewardFactor + Generator.generateCoins() * this.todo.tasks.length * perTaskRewardFactor
      executor.addExperience(experience)
      executor.addCoins(coins)
      /*eslint-disable */
      Materialize.toast('You have done it! This is your reward: ' + experience + ' exp and ' + coins + ' coins', 4000)
      /*eslint-enable */
    },
    deleteTag: function (index) {
      this.todo.tags.splice(index, 1)
    },
    setTag: function (tag) {
      this.$emit('selectTag', tag)
    }
  }
}
</script>

<style scoped>
</style>
