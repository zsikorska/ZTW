<template>

  <div id="authors-table">
    <v-table
      :data="authorSource"
      :pageSize="3"
      :currentPage.sync="currentPage"
      @totalPagesChanged="totalPages = $event"
    >
      <thead slot="head">
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th></th>
        <th></th>
      </thead>

      <tbody slot="body" slot-scope="{ displayData }">
        <tr v-for="author in displayData" :key="author.id">
          <td>{{ author.id }}</td>
          <td>{{ author.firstName }}</td>
          <td>{{ author.lastName }}</td>
          <td>
            <button
              @click="
                $emit('update:author', JSON.parse(JSON.stringify(author)))
              "
            >
              UPDATE
            </button>
          </td>
          <td>
            <button @click="$emit('delete:author', author.id)">DELETE</button>
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
  name: "authors-table",
  props: {
    authorSource: Array,
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