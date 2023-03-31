<template>
  <div id="books-table">
    <v-table
      :data="bookSource"
      :pageSize="3"
      :currentPage.sync="currentPage"
      @totalPagesChanged="totalPages = $event"
    >
      <thead slot="head">
        <th>Id</th>
        <th>Title</th>
        <th>Authors</th>
        <th>Pages</th>
        <th></th>
        <th></th>
      </thead>
      <tbody slot="body" slot-scope="{ displayData }">
        <tr v-for="book in displayData" :key="book.id">
          <td>{{ book.id }}</td>
          <td>{{ book.title }}</td>
          <td>
            <tr v-for="author in book.authors" :key="author.id">
              <td>{{ author.firstName + " " + author.lastName }}</td>
            </tr>
          </td>
          <td>{{ book.pages }}</td>
          <td>
            <button
              @click="$emit('update:book', JSON.parse(JSON.stringify(book)))"
            >
              UPDATE
            </button>
          </td>
          <td>
            <button @click="$emit('delete:book', book.id)">DELETE</button>
          </td>
        </tr>
      </tbody>
    </v-table>
    <smart-pagination
      :currentPage.sync="currentPage"
      :totalPages="totalPages"
    />
  </div>
</template>
<script>
export default {
  name: "books-table",
  props: {
    bookSource: Array,
  },
  data() {
    return {
      currentPage: 1,
      totalPages: 0,
    };
  },
};
</script>
<style scoped></style>