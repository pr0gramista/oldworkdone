Vue.component('task', {
  props: ['task', 'todo', 'index'],
  template:
  `
  <li class="task hover-visible-parent-3">
    <div class="inline-parent">
      <input type="checkbox" v-model="task.done" class="filled-in" :id="'todo-' + todo.id + '-task-' + index"/>
      <label :for="'todo-' + todo.id + '-task-' + index" class="grow">
        <input v-model="task.content" type="text" class="generic" />
      </label>
      <button class="hover-visible-3 clear"><i class="material-icons">delete</i></button>
    </div>
  </li>
  `
})
