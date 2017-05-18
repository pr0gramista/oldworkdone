<template>
  <li class="task hover-visible-parent-3">
    <div class="inline-parent">
      <input type="checkbox" v-model="task.done" class="filled-in" :id="unique"/>
      <label :for="unique" class="grow">
        <input v-model="task.content" type="text" class="generic" />
      </label>
      <button @click="deleteMe()" class="hover-visible-3 clear"><i class="material-icons">delete</i></button>
    </div>
  </li>
</template>

<script>
import Generator from '@/Generator'

export default {
  name: 'habit',
  props: ['task', 'todo', 'index'],
  data: function () {
    return {
      unique: Generator.uid()
    }
  },
  methods: {
    deleteMe: function () {
      this.$emit('deleteThisTask')
    }
  }
}
</script>

<style scoped>
</style>
