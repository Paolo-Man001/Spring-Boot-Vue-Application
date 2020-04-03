<template>
  <div id="app">
    <Todos />
    <footer class="info">
      <p>Based on a project written by <a href="#">Evan You</a></p>
      <p>Original Vue TodoApp project is <a href="#">here</a></p>
      <p>Modified for this tutorial by Andrew Hughes</p>
    </footer>
  </div>
</template>

<script>
import Todos from "./components/Todos.vue";

export default {
  name: "App",
  components: {
    // eslint-disable-next-line vue/no-unused-components
    Todos
  },
  data: () => {
    return {};
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}
[v-cloak] {
  display: none;
}
</style>
