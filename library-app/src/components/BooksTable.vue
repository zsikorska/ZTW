<template>
  <div id="books-table">
    <table>
      <!-- ...thead... -->
      <tbody>
        <tr v-for="book in bookSource" :key="book.id">
          <td>{{ book.title }}</td>
          <td>{{ book.pages }}</td>
          <td>{{ book.authors }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  name: "books-table",
  props: {
    bookSource: Array,
  },
};
</script>
<style scoped></style>