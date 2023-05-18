<template>
  <div id="app" class="small-container">
    <h1>Books</h1>
    <book-form
      :bookDataSource="bookData"
      @add:book="addBook"
      @update:book="updateBook"
    />
    <books-table
      :bookSource="books"
      @delete:book="deleteBook"
      @update:book="sendBookData"
    />
  </div>
</template>

<script>
import BooksTable from "@/components/books/BooksTable.vue";
import BookForm from "@/components/books/BookForm.vue";
import LayoutDefault from "@/layouts/LayoutDefault.vue";

export default {

    name: 'Books',
    created() {
        this.$emit('update:layout', LayoutDefault);
    },

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
        pages: 0,
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

      try {
        const response = await fetch(
          "http://localhost:8080/books",
          requestOptions
        );
        const data = await response.json();
        this.books = [...this.books, data];
      } catch (error) {
        console.error(error);
      }
    },
    async updateBook(book) {
      const requestOptions = {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book),
      };
      try {
        await fetch("http://localhost:8080/books/" + book.id, requestOptions);
        this.getBooks();
      } catch (error) {
        console.error(error);
      }
    },
    async deleteBook(index) {
      await fetch("http://localhost:8080/books/" + index, {
        method: "DELETE",
      });
      try {
        this.getBooks();
      } catch (error) {
        console.error(error);
      }
    },
    sendBookData(book) {
      this.bookData = book;
    },
  },
  mounted() {
    this.getBooks();
  },
};
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css">
</style>