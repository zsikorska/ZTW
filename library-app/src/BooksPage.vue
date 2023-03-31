<template>
  <div id="app" class="small-container">
  <h1>Books</h1>    
  <book-form :bookDataSource="bookData" @add:book="addBook" @update:book="updateBook"/>
  <books-table :bookSource="books" @delete:book="deleteBook" @update:book="sendBookData" />
  </div>
</template>
<script>
import BooksTable from "@/components/BooksTable.vue";
import BookForm from "@/components/BookForm.vue";

export default {
  name: "app",
  components: {
    BooksTable,
    BookForm,
  },
  data() {
    return {
      books: [],
      bookData: {
        title: "",
        authors: [],
        pages: 0
      },
    };
  },
  methods: {
    async getBooks() {
      try {
        const response = await fetch("http://localhost:8080/books");
        const data = await response.json();
        this.books = data;
      } catch (error) {
        console.error(error);
      }
    },
    async addBook(book) {
      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book),
      };
      const response = await fetch(
        "http://localhost:8080/books",
        requestOptions
      );
      const data = await response.json();
      this.books = [...this.books, data];
    },
    async updateBook(book) {
      const requestOptions = {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book),
      };
      const response = await fetch(
        "http://localhost:8080/books/" + book.id,
        requestOptions
      );
      console.log(response);
      this.getBooks();
    },
    async deleteBook(index) {
      console.log(index);
      const response = await fetch("http://localhost:8080/books/" + index, {
        method: "DELETE",
      });
      console.log(response);
      this.getBooks();
    },
    sendBookData(book) {
      console.log(book)
      this.bookData = book
    },
  },
  mounted() {
    this.getBooks();
  },
}
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>