<template>
  <div class="habit grid-item">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content hover-visible-parent">
        <textarea v-model="habit.text" />
        <button @click="done" class="center-align waves-effect waves-light btn">Done</button>

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
        </div>
        <div :id="'chips-' + unique" class="chips">
          <tag v-for="(text, index) in habit.tags
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
import Generator from '@/Generator'
import Vue from 'vue'
import Draggabilly from 'draggabilly'
import _ from 'lodash'
import executor from '@/executor'

export default {
  name: 'habit',
  props: ['habit', 'index', 'grid'],
  components: {
    Tag
  },
  watch: {
    habit: {
      handler: _.debounce(function (habit) {
        this.$emit('saveHabit', this.habit)
      }, 500),
      deep: true
    }
  },
  data: function () {
    return {
      unique: Generator.uid(),
      newTag: ''
    }
  },
  computed: {
    cardClass: function () {
      return 'color-' + this.habit.color.toLowerCase()
    },
    colorDropdownClass: function () {
      return this.habit.color.toLowerCase()
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
      this.habit.color = color
    },
    done: function () {
      const experience = Generator.generateExperience()
      const coins = Generator.generateCoins()
      executor.addExperience(experience)
      executor.addCoins(coins)
      /*eslint-disable */
      Materialize.toast('You have received ' + experience + ' exp and ' + coins + ' coins', 4000)
      /*eslint-enable */
    },
    addTag: function () {
      if (this.newTag.length > 0) {
        // Create tag list if it doesn't exist
        if (this.habit.tags === undefined) {
          this.$set(this.habit, 'tags', [])
        }

        this.habit.tags.push(this.newTag)
        this.newTag = ''
      }
    },
    deleteTag: function (index) {
      this.habit.tags.splice(index, 1)
    },
    selectTag: function (index) {
      this.$emit('selectTag', this.habit.tags[index])
    },
    setTag: function (tag) {
      this.$emit('selectTag', tag)
    }
  }
}
</script>

<style scoped>
</style>
