<template>
    <div id="rentals-table">
        <v-table
                :data="rentalSource"
                :pageSize="3"
                :currentPage.sync="currentPage"
                @totalPagesChanged="totalPages = $event"
        >
            <thead slot="head">
            <th>Id</th>
            <th>Book</th>
            <th>Reader</th>
            <th>Rental date</th>
            <th>Return date</th>
            <th>Returned</th>
            <th></th>
            <th></th>
            </thead>

            <tbody slot="body" slot-scope="{ displayData }">
            <tr v-for="rental in displayData" :key="rental.id">
                <td>{{ rental.id }}</td>
                <td>{{ rental.book.title }}</td>
                <td>{{ rental.reader.firstName + " " + rental.reader.lastName }}</td>
                <td>{{ rental.dateOfRental }}</td>
                <td>{{ rental.dateOfReturn }}</td>
                <td>{{ rental.returned }}</td>
                <td>
                    <b-button variant="primary" @click="$emit('update:rental', JSON.parse(JSON.stringify(rental)))">UPDATE</b-button>
                </td>
                <td>
                    <b-button variant="danger" @click="$emit('delete:rental', rental.id)">DELETE</b-button>
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
    name: "rentals-table",
    props: {
        rentalSource: Array,
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