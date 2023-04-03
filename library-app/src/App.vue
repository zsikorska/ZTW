<script>

export default {
  data() {
    return {
      
    };
  },
  
};
</script>

<template>
  <div id="app">
    <b-nav>
    <b-nav-item ><router-link :to="{name: 'home'}">Home</router-link></b-nav-item>
    <b-nav-item><router-link :to="{name: 'books'}">Books</router-link></b-nav-item>
    <b-nav-item><router-link :to="{name: 'authors'}">Authors</router-link></b-nav-item>
    <b-nav-item><router-link :to="{name: 'rentals'}">Rentals</router-link></b-nav-item>
  </b-nav>
    <router-view></router-view>
  </div>
</template>


