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
    
  <b-navbar toggleable="lg" type="dark" variant="primary">
    <b-nav>
    <b-navbar-brand ><router-link :to="{name: 'home'}">LibraryApp</router-link></b-navbar-brand>
    <b-nav-item ></b-nav-item>
    <b-nav-item active><router-link :to="{name: 'books'}">Books</router-link></b-nav-item>
    <b-nav-item><router-link :to="{name: 'authors'}">Authors</router-link></b-nav-item>
    <b-nav-item><router-link :to="{name: 'readers'}">Readers</router-link></b-nav-item>
    <b-nav-item><router-link :to="{name: 'rentals'}">Rentals</router-link></b-nav-item>
  </b-nav>
  </b-navbar>
    <router-view></router-view>
  </div>
</template>

<style>
.navbar a{
  text-decoration: none;
  color: white;
}
.navbar a:hover{
  color: #fd9c0d;
}
.navbar-brand {
  padding: 10px;
}
</style>


