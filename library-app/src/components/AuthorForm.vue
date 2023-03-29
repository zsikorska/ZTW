<template>
  <div id="author-form">
    <form @submit.prevent="handleSubmit">
      <label>First name</label>
      <input
        v-model="author.firstName"
        type="text"
        :class="{ 'has-error': submitting && invalidName }"
        @focus="clearStatus"
        @keypress="clearStatus"
      />
      <label>Last name</label>
      <input
        v-model="author.lastName"
        type="text"
        :class="{ 'has-error': submitting && invalidName }"
        @focus="clearStatus"
      />
      <p v-if="error && submitting" class="error-message">
        Fill out requested fields
      </p>
      <p v-if="success" class="success-message">Data has been saved successfully</p>
      <button>Save</button>
    </form>
  </div>
</template>
<script>
export default {
  name: "author-form",
  data() {
    return {
      submitting: false,
      error: false,
      success: false,
      author: {
        firstName: "",
        lastName: "",
      },
    };
  },
  methods: {
    handleSubmit() {
      this.submitting = true;
      this.clearStatus();
      //check form fields
      if (this.invalidName) {
        this.error = true;
        return;
      }
      this.$emit("add:author", this.author);
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
  },
  computed: {
    invalidName() {
      return this.author.firstName === "";
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