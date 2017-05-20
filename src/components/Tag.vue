<template>
  <div class="tag chip" @click="selectMe()">
    {{ text }}
    <i @click="deleteMe()" class="close material-icons">close</i>
  </div>
</template>

<script>
export default {
  name: 'tag',
  props: ['text', 'index'],
  data: function () {
    return {
      selected: false
    }
  },
  methods: {
    deleteMe: function () {
      this.$emit('deleteThisTag')
    },
    selectMe: function (index) {
      this.$emit('selectTag', this.text)
    }
  }
}
</script>

<style scoped>
</style>
