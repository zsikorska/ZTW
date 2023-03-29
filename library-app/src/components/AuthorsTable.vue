<template>
  <div id="authors-table">
    <table>
      <!-- ...thead... -->
      <tbody>
        <tr v-for="author in authorSource" :key="author.id">
          <td>{{ author.firstName }}</td>
          <td>{{ author.lastName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  name: "authors-table",
  props: {
    authorSource: Array,
  },
};
</script>
<style scoped></style>