<script>
import Home from "./HomePage.vue";
import Authors from "./AuthorsPage.vue";
import Books from "./BooksPage.vue";
import Rentals from "./RentalsPage.vue";

const routes = {
  "/": Home,
  "/authors": Authors,
  "/books": Books,
  "/rentals": Rentals,
};

export default {
  data() {
    return {
      currentPath: window.location.hash,
    };
  },
  computed: {
    currentView() {
      return routes[this.currentPath.slice(1) || "/"];
    },
  },
  mounted() {
    window.addEventListener("hashchange", () => {
      this.currentPath = window.location.hash;
    });
  },
};
</script>

<template>
  <div>
    <a href="#/">Home |</a>
    <a href="#/authors">Authors |</a>
    <a href="#/books">Books |</a>
    <a href="#/rentals">Rentals</a>
    <component :is="currentView" />
  </div>
</template>


