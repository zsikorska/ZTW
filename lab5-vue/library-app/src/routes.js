
import HomePage from "./components/HomePage.vue";
import AuthorsPage from "./components/authors/AuthorsPage.vue";
import BooksPage from "./components/books/BooksPage.vue";
import RentalsPage from "./components/rentals/RentalsPage.vue";
import ReadersPage from "@/components/readers/ReadersPage.vue";

export default [
    {
      path: '/',
      component: HomePage,
      name: 'home'
    },
    {
      path: '/authors',
      component: AuthorsPage,
      name: 'authors'
    },
    {
      path: '/books',
      component: BooksPage,
      name: 'books'
    },
    {
      path: '/readers',
      component: ReadersPage,
      name: 'readers'
    },
    {
      path: '/rentals',
      component: RentalsPage,
      name: 'rentals'
    }
  ]