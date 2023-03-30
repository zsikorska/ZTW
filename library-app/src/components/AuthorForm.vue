<template>
  <div id="author-form">
    <form>
      <!-- TODO investigate why author data doesnt show up unless below line present -->
      <div v-if="author.id != null">
        <p v-text="`Author id: ${author.id}`"></p>
      </div>
      <label>First name</label>
      <input
        v-model="author.firstName"
        type="text"
        :class="{ 'has-error': submitting && blankFirstName }"
        @focus="clearStatus"
        @keypress="clearStatus"
      />
      <label>Last name</label>
      <input
        v-model="author.lastName"
        type="text"
        :class="{ 'has-error': submitting && blankLastName }"
        @focus="clearStatus"
      />
      <p v-if="error && submitting" class="error-message">
        Fill out requested fields
      </p>
      <p v-if="success" class="success-message">
        Data has been saved successfully
      </p>
      <button @click="handleSubmit">Save</button>
      <button v-if="author.id != null" @click="reset">Reset</button>
    </form>
  </div>
</template>
<script>
export default {
  name: "author-form",
  props: {
    authorDataSource: Object,
  },
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      author: JSON.parse(JSON.stringify(this.authorDataSource)),
    };
  },

  watch: {
    authorDataSource() {
      this.author = JSON.parse(JSON.stringify(this.authorDataSource));
    },
  },

  methods: {
    handleSubmit() {
      this.submitting = true;
      this.clearStatus();
      //check form fields
      if (this.blankFirstName || this.blankLastName) {
        this.error = true;
        return;
      }
      if (this.author.id == null) {
        this.$emit("add:author", this.author);
        console.log("adding");
      } else {
        this.$emit("update:author", this.author);
        console.log("updating");
      }
      //clear form fields
      this.author = {
        firstName: "",
        lastName: "",
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
      this.author = JSON.parse(JSON.stringify(this.authorDataSource));
      // this.author = this.getAuthor(this.author.id)
      console.log(this.authorDataSource);
    },
  },
  computed: {
    blankFirstName() {
      return (
        this.author.firstName === "" || this.author.firstName.trim() === ""
      );
    },
    blankLastName() {
      return (
        this.author.lastName.trim() === "" || this.author.lastName.trim() === ""
      );
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