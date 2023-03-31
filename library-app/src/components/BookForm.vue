<template>
  <div id="book-form">
    <form>
      <div v-if="book.id != null">
        <p v-text="`Book id: ${book.id}`"></p>
      </div>
      <label>Title</label>
      <input
        v-model="book.title"
        type="text"
        :class="{ 'has-error': submitting && blankTitle }"
        @focus="clearStatus"
        @keypress="clearStatus"
      />
      <label>Authors</label>
      <multiselect id='multiselect' v-model="book.authorIds" :options="options" :multiple="true" :value="book.authorIds" :customLabel="authorsName" track-by="id"></multiselect>
      <label>Pages</label>
      <input
        v-model="book.pages"
        type="number"
        :class="{ 'has-error': submitting && invalidPageNumber }"
        @focus="clearStatus"
        @keypress="clearStatus"
      />
      <p v-if="error && submitting" class="error-message">
        Fill out requested fields
      </p>
      <p v-if="success" class="success-message">
        Data has been saved successfully
      </p>
      <button @click="handleSubmit">Save</button>
      <button v-if="book.id != null" @click="reset">Reset</button>
    </form>
  </div>
</template>
<script>
export default {
  name: "book-form",
  props: {
    bookDataSource: Object,
  },
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      options: [],
      allAuthors: Array,
      book: {
        id: this.bookDataSource.id,
        title: this.bookDataSource.title,
        authorIds:  JSON.parse(JSON.stringify(this.bookDataSource.authors)),
        pages: this.bookDataSource.pages,
      },
      currentAuthors: this.bookDataSource,
    };
  },

  watch: {
    bookDataSource() {
        console.log(this.book.authorIds)
      this.book = {
        id: this.bookDataSource.id,
        title: this.bookDataSource.title,
        authorIds: JSON.parse(JSON.stringify(this.bookDataSource.authors)),
        pages: this.bookDataSource.pages,
      };
    },
  },
  mounted() {
      this.getAuthors().then((authors) => this.options = authors);
    },

  methods: {
    async getAuthors() {
      try {
        const response = await fetch("http://localhost:8080/authors");
        const data = await response.json();
        return data;
      } catch (error) {
        console.error(error);
      }
    },
    authorsName ({ firstName, lastName }) {
      return `${firstName} ${lastName}`
    },
    
    handleSubmit() {
      this.submitting = true;
      this.clearStatus();
      //check form fields
      if (this.blankTitle || this.invalidPageNumber) {
        this.error = true;
        return;
      }
      if (this.book.id == null) {        
        this.book.authorIds = this.book.authorIds.map((a) => a.id)
        this.$emit("add:book", this.book);
        console.log("adding");
      } else {   
        this.book.authorIds = this.book.authorIds.map((a) => a.id)
        this.$emit("update:book", this.book);
        console.log("updating");
      }
      //clear form fields
      this.book = {
        title: "",
        authorIds: [],
        pages: 0,
      };
      this.error = false;
      this.success = true;
      this.submitting = false;
    },
    clearStatus() {
      this.success = false;
      this.error = false;
    },
    reset() {
      this.book = JSON.parse(JSON.stringify(this.bookDataSource));
      this.book.authorIds = JSON.parse(JSON.stringify(this.bookDataSource.authors));
      // this.book = this.getbook(this.book.id)
      console.log(this.bookDataSource);
    },
  },
  computed: {
    blankTitle() {
      return this.book.title.trim() === "";
    },
    invalidPageNumber() {
      return this.book.pages <= 0;
    },
  },
};
</script>
<style scoped>
form {
  margin-bottom: 2rem;
}
[class*="-message"] {
  font-weight: 500;
}
.error-message {
  color: #d33c40;
}
.success-message {
  color: #32a95d;
}
</style>