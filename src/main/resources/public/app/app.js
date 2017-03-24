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
        newHabit.id = null;
      })
    },
    refreshTodos: function () {
      habitRepository.fetch();
    }
  }
})

Vue.component('habit', {
  props: ['habit', 'index'],
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
  computed: {
    cardClass: function () {
      return "color-" + this.habit.color.toLowerCase();
    }
  },
  methods: {
    startEdit: function () {
      this.is_editing = true;
      this.edit_text = this.habit.text;

      Vue.nextTick(function () {
        $('.dropdown-button').dropdown({
            inDuration: 300,
            outDuration: 225,
            constrainWidth: false, // Does not change width of dropdown to that of the activator
            hover: true, // Activate on hover
            gutter: 0, // Spacing from edge
            belowOrigin: false, // Displays dropdown below the button
            alignment: 'left', // Displays dropdown with edge aligned to the left of button
            stopPropagation: false // Stops event propagation
          }
        );
      })
    },
    endEdit: function () {
      this.is_editing = false;
      this.habit.text = this.edit_text;
    },
    setColor: function (color) {
      this.habit.color = color;
    }
  },
  template:
  `<div class="habit col s12 m6 l4">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content">
        <i v-if="!is_editing" @click="startEdit" class="material-icons right">edit</i>
        <i v-if="is_editing" @click="endEdit" class="material-icons right">done</i>
        <p v-if="!is_editing">{{ habit.text }}</p>
        <textarea v-if="is_editing" v-model="edit_text" />
        <div v-if="is_editing">
          <a class='dropdown-button btn' href='#' :data-activates="'dropdown-' + index">Color</a>

          <!-- Dropdown Structure -->
          <ul :id="'dropdown-' + index" class='dropdown-content'>
            <li><a href="#" @click="setColor('RED')"><div class="color-icon red"></div></a></li>
            <li><a href="#" @click="setColor('BLUE')"><div class="color-icon blue"></div></a></li>
            <li><a href="#" @click="setColor('GREEN')"><div class="color-icon green"></div></a></li>
          </ul>
        </div>
      </div>
      <div class="card-action">
        <button class="waves-effect waves-light btn">Done</button>
      </div>
    </div>
  </div>`
})

setInterval(app.refreshTodos, 5000);
