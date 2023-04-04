<template>
    <div class="LayoutDefault">
        <div class="LayoutDefault__nav">
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
        </div>

        <main class="LayoutDefault__main">
            <slot/>
        </main>

    </div>
</template>

<script>
export default {
    name: "LayoutDefault"
}
</script>

<style scoped>

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