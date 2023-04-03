<template>
    <div id="app" class="small-container">
        <h1>Rentals</h1>
        <rental-form
                :rentalDataSource="rentalData"
                :availableBooksSource="availableBooks"
                @add:rental="addRental"
                @update:rental="updateRental"
        />
        <rentals-table
                :rentalSource="rentals"
                @delete:rental="deleteRental"
                @update:rental="sendRentalData"
        />
    </div>
</template>
<script>
import RentalsTable from "@/components/rentals/RentalsTable.vue";
import RentalForm from "@/components/rentals/RentalForm.vue";

export default {
    name: "app",
    components: {
        RentalsTable,
        RentalForm,
    },
    data() {
        return {
            rentals: [],
            rentalData: {
                book: null,
                reader: null,
                dateOfRental: "",
                dateOfReturn: "",
                returned: false,
            },
            availableBooks: [],
        };
    },
    methods: {
        async getRentals() {
            try {
                const response = await fetch("http://localhost:8080/rentals");
                const data = await response.json();
                this.rentals = data;
            } catch (error) {
                console.error(error);
            }
        },
        async addRental(rental) {
            const requestOptions = {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(rental),
            };

            try {
                const response = await fetch(
                    "http://localhost:8080/rentals",
                    requestOptions
                );
                const data = await response.json();
                this.rentals = [...this.rentals, data];
                this.getAvailableBooks();
            } catch (error) {
                console.error(error);
            }
        },
        async updateRental(rental) {
            const requestOptions = {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(rental),
            };
            try {
                await fetch("http://localhost:8080/rentals/" + rental.id, requestOptions);
                this.getRentals();
                this.getAvailableBooks();
            } catch (error) {
                console.error(error);
            }
        },
        async deleteRental(index) {
            await fetch("http://localhost:8080/rentals/" + index, {
                method: "DELETE",
            });
            try {
                this.getRentals();
                this.getAvailableBooks();
            } catch (error) {
                console.error(error);
            }
        },

        sendRentalData(rental) {
            this.rentalData = rental;
        },

        async getAvailableBooks() {
            try {
                const response = await fetch("http://localhost:8080/books/available");
                const data = await response.json();
                this.availableBooks = data;
            } catch (error) {
                console.error(error);
            }
        },
    },
    mounted() {
        this.getRentals();
        this.getAvailableBooks();
    },
};
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css">
</style>