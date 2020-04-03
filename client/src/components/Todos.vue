<template>
  <div>
    <h1 class="title">Todos</h1>
    <h1 class="email">{{ userEmail }}</h1>
    <section class="todoapp">
      <!-- V-iF -->
      <div v-if="loading">
        <h1 class="loading">Loading...</h1>
      </div>
      <!-- V-else -->
      <div v-else>
        <!-- Header -->
        <header class="header">
          <input
            type="text"
            class="new-todo"
            autofocus
            autocomplete="off"
            :placeholder="this.inputPlaceholder"
            v-model="newTodo"
            @keyup.enter="addTodo"
          />
        </header>

        <!-- Section -->
        <section class="main" v-show="todos.length" v-cloak>
          <input class="toggle-all" type="checkbox" v-model="allDone" />
          <ul class="todo-list">
            <li
              v-for="todo in filteredTodos"
              class="todo"
              :key="todo.id"
              :class="{ completed: todo.completed, editing: todo == editedTodo }"
            >
              <div class="view">
                <input
                  class="toggle"
                  type="checkbox"
                  v-model="todo.completed"
                  @change="completeTodo(todo)"
                />
                <label @dblclick="editTodo(todo)">{{ todo.title }}</label>
                <button class="destroy" @click="removeTodo(todo)">
                  Remove Todo
                </button>
              </div>
              <input
                class="edit"
                type="text"
                v-model="todo.title"
                v-todo-focus="todo == editedTodo"
                @blur="doneEdit(todo)"
                @keyup.enter="doneEdit(todo)"
                @keyup.esc="cancelEdit(todo)"
              />
            </li>
          </ul>
        </section>

        <!-- Footer -->
        <footer class="footer" v-show="todos.length" v-cloak>
          <span class="todo-count">
            <strong>{{ remaining }}</strong
            >{{ remaining | pluralize }} left
          </span>
          <ul class="filters">
            <li>
              <a
                href="#/all"
                @click="setVisibility('all')"
                :class="{ selected: visibility == 'all' }"
                >All</a
              >
            </li>
            <li>
              <a
                href="#/active"
                @click="setVisibility('active')"
                :class="{ selected: visibility == 'active' }"
                >Active</a
              >
            </li>
            <li>
              <a
                href="#/completed"
                @click="setVisibility('completed')"
                :class="{ selected: visibility == 'completed' }"
                >Completed</a
              >
            </li>
          </ul>
          <button
            class="clear-completed"
            @click="removeCompleted"
            v-show="todos.length > remaining"
          >
            Clear Completed
          </button>
        </footer>
      </div>
    </section>
    <!-- Error -->
    <div v-if="error" class="error" @click="handleErrorClick">ERROR: {{ this.error }}</div>
  </div>
</template>

<script>
/* eslint-disable no-unused-vars */
// Visibility filters :
let filters = {
  all: function(todos) {
    return todos;
  },
  active: function(todos) {
    return todos.filter(function(todo) {
      return !todo.completed;
    });
  },
  completed: function(todos) {
    return todos.filter(function(todo) {
      return todo.completed;
    });
  }
};

// app Vue instance :
export default {
  name: "Todos",
  props: {
    activeUser: Object
  },

  // DATA : app Initial State :
  data: () => {
    return {
      todos: [],
      newTodo: "",
      editedTodo: null,
      visibility: "all",
      loading: true,
      error: null
    };
  },

  // LIFE-CYCLE Hooks :
  mounted() {
    // Inject some startup data
    this.todo = [
      { title: "Drink Coffee", completed: false },
      { title: "Write REST API", completed: false },
      { title: "Finish Vue + Spring-Boot App", completed: false }
    ];

    // hide the loading message :
    this.loading = false;
  },

  // COMPUTED Poperties :
  computed: {
    filteredTodos: function() {
      return filters[this.visibility](this.todos);
    },
    remaining: function() {
      return filters.active(this.todos).length;
    },
    allDone: {
      get: function() {
        return this.remaining === 0;
      },
      set: function(value) {
        this.todos.forEach(todo => {
          todo.completed = value;
        });
      },
      userEmail: function() {
        return this.activeUser ? this.activeUser.email : "";
      },
      inputPlaceholder: function() {
        return this.activeUser
          ? this.activeUser.given_name + ", what needs to be done?"
          : "What needs to be done?";
      }
    },

    //-- pularlize :
    filters: {
      pluralize: function(n) {
        return n === 1 ? "item" : "items";
      }
    }
  },

  // METHODS :  Implement Data Logic : (No DOM Manipulation here)
  methods: {
    addTodo: function() {
      let value = this.newTodo && this.newTodo.trim();
      if (!value) {
        return;
      }

      this.todo.push({
        title: value,
        completed: false
      });

      this.newTodo = "";
    },

    setVisibility: function(vis) {
      this.visibility = vis;
    },

    competeTodo(todo) {
      // return todo.completed = true;
    },

    removeTodo: function(todo) {
      this.todo.splice(this.todos.indexOf(todo), 1);
    },

    editTodo: function(todo) {
      this.beforeEditCache = todo.title;
      this.editedTodo = todo;
    },

    doneEdit: function(todo) {
      if (!this.editedTodo) {
        return;
      }

      this.editedTodo = null;
      todo.title = todo.title.trim();

      if (!todo.title) {
        this.removeTodo(todo);
      }
    },

    cancelEdit: function(todo) {
      this.editedTodo = null;
      todo.title = this.beforeEditCache;
    },

    removeCompleted: function() {
      this.todos = filters.active(this.todos);
    },

    handleErrorClick: function() {
      this.error = null;
    }
  },

  // Custom Diretives to wait for the DOM to be updated
  // before focusing on the input field.
  // See 'custom-directives.html Vue.js webiste
  directives: {
    "todo-focus": function(el, binding) {
      if (binding.value) {
        el.focus();
      }
    }
  }
};
</script>

<style></style>
