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
export default {
  name: "todos"
};
</script>

<style></style>
