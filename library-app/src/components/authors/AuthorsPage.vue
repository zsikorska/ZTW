<template>
  <div id="app" class="small-container">
    <h1>Authors</h1>
    <author-form :authorDataSource="authorData" @add:author="addAuthor" @update:author="updateAuthor"/>
    <authors-table :authorSource="authors" @delete:author="deleteAuthor" @update:author="sendAuthorData" />
  </div>
</template>
<script>
import AuthorsTable from "@/components/authors/AuthorsTable.vue";
import AuthorForm from "@/components/authors/AuthorForm.vue";

export default {
  name: "app",
  components: {
    AuthorsTable,
    AuthorForm,
  },
  data() {
    return {
      authors: [],
      authorData: {
        firstName: "",
        lastName: ""
      }
    };
  },
  methods: {
    async getAuthors() {
      try {
        const response = await fetch("http://localhost:8080/authors");
        const data = await response.json();
        this.authors = data;
      } catch (error) {
        console.error(error);
      }
    },
    async addAuthor(author) {
      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(author),
      };
      const response = await fetch(
        "http://localhost:8080/authors",
        requestOptions
      );
      const data = await response.json();
      this.authors = [...this.authors, data];
    },
    async updateAuthor(author) {
      const requestOptions = {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(author),
      };
      const response = await fetch(
        "http://localhost:8080/authors/" + author.id,
        requestOptions
      );
      console.log(response);
      this.getAuthors()
    },
    async deleteAuthor(index) {
      console.log(index)
      const response = await fetch("http://localhost:8080/authors/" + index, {
        method: "DELETE",
      });
      console.log(response);
      this.getAuthors()
    },
    sendAuthorData(author) {
      console.log(author)
      this.authorData = author
    },
  },
  mounted() {
    this.getAuthors();
  },
};
</script>
<style>
button {
  background: #009435;
  border: 1px solid #009435;
}
.small-container {
  max-width: 680px;
  margin: 40px auto;
}
</style>
