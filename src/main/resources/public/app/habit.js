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
            hover: true
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
    },
    done: function() {
      axios.get("/habit/" + this.habit.id + "/done/").then(function (r) {
        var exp = r.data.experience
        var coins = r.data.coins
        Materialize.toast('You have received ' + exp + ' exp and ' + coins + ' coins', 4000)
        userRepository.fetch();
      });
    }
  },
  template:
  `<div class="habit col s12 m6 l4">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content visible-parent-hover">
        <i v-if="!is_editing" @click="startEdit" class="material-icons right visible-child-hover">edit</i>
        <i v-if="is_editing" @click="endEdit" class="material-icons right">done</i>
        <p v-if="!is_editing">{{ habit.text }}</p>
        <textarea v-if="is_editing" v-model="edit_text" />
        <button @click="done" class="center-align waves-effect waves-light btn">Done</button>

        <div class="card-settings" v-if="is_editing">
          <a class='dropdown-button' href='#' :data-activates="'dropdown-' + index">
            <div class="color-icon" v-bind:class="colorDropdownClass"></div>
          </a>
          <ul :id="'dropdown-' + index" class='dropdown-content'>
            <li><a href="#" @click="setColor('RED')"><div class="color-icon red"></div></a></li>
            <li><a href="#" @click="setColor('BLUE')"><div class="color-icon blue"></div></a></li>
            <li><a href="#" @click="setColor('GREEN')"><div class="color-icon green"></div></a></li>
            <li><a href="#" @click="setColor('WHITE')"><div class="color-icon white"></div></a></li>
            <li><a href="#" @click="setColor('GRAY')"><div class="color-icon gray"></div></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>`
})
