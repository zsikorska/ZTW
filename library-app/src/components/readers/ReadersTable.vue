<template>

    <div id="readers-table">
        <v-table
                :data="readerSource"
                :pageSize="3"
                :currentPage.sync="currentPage"
                @totalPagesChanged="totalPages = $event"
        >
            <thead slot="head">
            <th>Id</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th></th>
            <th></th>
            </thead>

            <tbody slot="body" slot-scope="{ displayData }">
            <tr v-for="reader in displayData" :key="reader.id">
                <td>{{ reader.id }}</td>
                <td>{{ reader.firstName }}</td>
                <td>{{ reader.lastName }}</td>
                <td>{{ reader.email }}</td>
                <td>
                    <b-button variant="primary" @click="$emit('update:reader', JSON.parse(JSON.stringify(reader)))">UPDATE</b-button>
                </td>
                <td>
                    <b-button variant="danger" @click="$emit('delete:reader', reader.id)">DELETE</b-button>
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
    name: "readers-table",
    props: {
        readerSource: Array,
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