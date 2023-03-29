<template>
  <div id="authors-table">
    <table>
      <!-- ...thead... -->
      <tbody>
        <tr v-for="author in authorSource" :key="author.id">
          <td>{{ author.id }}</td>
          <td>{{ author.firstName }}</td>
          <td>{{ author.lastName }}</td>
          <td><button @click="$emit('update:author', JSON.parse(JSON.stringify(author)))">UPDATE</button></td>
          <td><button @click="$emit('delete:author', author.id)">DELETE</button></td>
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