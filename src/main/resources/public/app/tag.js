Vue.component('tag', {
  props: ['text', 'index'],
  data: function() {
    return {
      selected: false
    }
  },
  methods: {
    deleteMe: function () {
      this.$emit("deleteThisTag");
    },
    selectMe: function (index) {
      app.$emit("selectTag", this.text);
    }
  },
  template:
  `<div class="tag chip" @click="selectMe()">
    {{ text }}
    <i @click="deleteMe()" class="close material-icons">close</i>
   </div>`
})
