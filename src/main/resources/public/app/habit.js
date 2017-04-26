Vue.component('habit', {
  props: ['habit', 'index'],
  watch: {
    habit: {
      handler: function (habit) {
        habitRepository.save(habit);
      },
      deep: true
    }
  },
  data: function() {
    return {
      unique: uid()
    }
  },
  computed: {
    cardClass: function () {
      return "color-" + this.habit.color.toLowerCase();
    },
    colorDropdownClass: function () {
      return this.habit.color.toLowerCase();
    }
  },
  mounted: function () {
    Vue.nextTick(function () {
      $('.dropdown-button').dropdown({
          hover: true
        }
      );
    })
  },
  methods: {
    setColor: function (color) {
      this.habit.color = color;
    },
    done: function() {
      axios.get("/habit/" + this.habit.id + "/done/").then(function (r) {
        reward(r.data.experience, r.data.coins);
      });
    }
  },
  template:
  `<div class="habit col s12 m6 l4">
    <div class="card" v-bind:class="cardClass">
      <div class="card-content hover-visible-parent">
        <textarea v-model="habit.text" />
        <button @click="done" class="center-align waves-effect waves-light btn">Done</button>

        <div class="card-settings hover-visible">
          <a class='dropdown-button' href='#' :data-activates="'dropdown-' + unique">
            <div class="color-icon" v-bind:class="colorDropdownClass"></div>
          </a>
          <ul :id="'dropdown-' + unique" class='dropdown-content'>
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
