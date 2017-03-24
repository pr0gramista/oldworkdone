var habitRepository = {
  fetch: function () {
    axios.get("/habit/").then(function (r) {
      app.habits = r.data;
    })
  },
  save: function (habit) {
    axios.put("/habit/" + habit.id, habit);
  }
}

var app = new Vue({
  el: '#app',
  data: {
    habits: habitRepository.fetch(),
    newHabit: {
      text: "przykladowy tekst",
      color: "RED",
      expReward: "HIGH",
      coinReward: "HIGH",
      tags: []
    }
  },
  watch: {
  },
  methods: {
    addNewHabit: function () {
      var newHabit = this.newHabit;
      axios.post("/habit/", newHabit).then(function (r) {
        newHabit.id = r.data; //id
        app.habits.push(newHabit);
      })
    },
    refreshTodos: function () {
      habitRepository.fetch();
    }
  }
})

Vue.component('habit', {
  props: ['habit'],
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
  methods: {
    startEdit: function () {
      this.is_editing = true;
      this.edit_text = this.habit.text;
    },
    endEdit: function () {
      this.is_editing = false;
      this.habit.text = this.edit_text;
    }
  },
  template:
  `<div class="habit col s12 m6 l4">
    <div class="card">
      <div class="card-content">
        <i v-if="!is_editing" @click="startEdit" class="material-icons right">edit</i>
        <i v-if="is_editing" @click="endEdit" class="material-icons right">done</i>
        <p v-if="!is_editing">{{ habit.text }}</p>
        <textarea v-if="is_editing" v-model="edit_text" />
      </div>
      <div class="card-action">
        <button class="waves-effect waves-light btn">Done</button>
      </div>
    </div>
  </div>`
})

setInterval(app.refreshTodos, 5000);
