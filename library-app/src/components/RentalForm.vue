<template>
    <div id="rental-form">
        <b-form>
            <div v-if="rental.id != null">
                <p class="form-header" v-text="`Rental id: ${rental.id}`"></p>
            </div>
            <div v-else>
                <p class="form-header">New rental</p>
            </div>
            <label>Book</label>
            <multiselect
                id="multiselect"
                v-model="rental.bookId"
                :options="optionsBooks"
                :multiple="false"
                :value="rental.bookId"
                :customLabel="bookTitle"
                track-by="id"
            ></multiselect>

            <label>Reader</label>
            <multiselect
                id="multiselect"
                v-model="rental.readerId"
                :options="optionsReaders"
                :multiple="false"
                :value="rental.readerId"
                :customLabel="readerName"
                track-by="id"
            ></multiselect>

            <label>Rental date</label>
            <input
                v-model="rental.dateOfRental"
                type="date"
                :class="{ 'has-error': submitting && blankBook }"
                @focus="clearStatus"
                @keypress="clearStatus"
            />

            <label>Return date</label>
            <input
                v-model="rental.dateOfReturn"
                type="date"
                :class="{ 'has-error': submitting && blankBook }"
                @focus="clearStatus"
                @keypress="clearStatus"
            />

            <input
                v-model="rental.returned"
                type="checkbox"
                id="returned"
                class="returned-checkbox"
                @focus="clearStatus"
                @keypress="clearStatus"
            />
            <label for="returned">The book has been returned</label>

            <p v-if="error && submitting" class="error-message">
                Fill out requested fields
            </p>
            <p v-if="success" class="success-message">
                Data has been saved successfully
            </p>
            <div class="form-buttons">
                <b-button variant="primary" @click="handleSubmit">Save</b-button>
                <b-button
                        variant="outline-primary"
                        v-if="rental.id != null"
                        @click="reset"
                >Reset</b-button
                >
            </div>
        </b-form>
    </div>
</template>
<script>
export default {
    name: "rental-form",
    props: {
        rentalDataSource: Object,
    },
    data() {
        return {
            submitting: false,
            error: false,
            success: false,
            optionsReaders: [],
            optionsBooks: [],
            rental: {
                id: this.rentalDataSource.id,
                bookId: JSON.parse(JSON.stringify(this.rentalDataSource.book)),
                readerId: JSON.parse(JSON.stringify(this.rentalDataSource.reader)),
                dateOfRental: this.rentalDataSource.dateOfRental,
                dateOfReturn: this.rentalDataSource.dateOfReturn,
                returned: this.rentalDataSource.returned,
            },
            currentBooks: this.rentalDataSource,
            currentReaders: this.rentalDataSource,
        };
    },

    watch: {
        rentalDataSource() {
            console.log(this.rental.bookId);
            console.log(this.rental.readerId);
            this.rental = {
                id: this.rentalDataSource.id,
                bookId: JSON.parse(JSON.stringify(this.rentalDataSource.book)),
                readerId: JSON.parse(JSON.stringify(this.rentalDataSource.reader)),
                dateOfRental: this.rentalDataSource.dateOfRental,
                dateOfReturn: this.rentalDataSource.dateOfReturn,
                returned: this.rentalDataSource.returned,
            };
        },
    },
    mounted() {
        this.getReaders().then((readers) => (this.optionsReaders = readers));
        this.getBooks().then((books) => (this.optionsBooks = books));
    },

    methods: {
        async getReaders() {
            try {
                const response = await fetch("http://localhost:8080/readers");
                const data = await response.json();
                return data;
            } catch (error) {
                console.error(error);
            }
        },

        async getBooks() {
            try {
                const response = await fetch("http://localhost:8080/books");
                const data = await response.json();
                return data;
            } catch (error) {
                console.error(error);
            }
        },

        readerName({ firstName, lastName }) {
            return `${firstName} ${lastName}`;
        },

        bookTitle({ title }) {
            return `${title}`;
        },

        handleSubmit() {
            this.submitting = true;
            this.clearStatus();
            //check form fields
            if (this.blankBook) {
                this.error = true;
                return;
            }
            if (this.rental.id == null) {
                this.rental.bookId = this.rental.bookId.map((a) => a.id);
                this.rental.readerId = this.rental.readerId.map((a) => a.id);
                this.$emit("add:rental", this.rental);
            } else {
                this.rental.bookId = this.rental.bookId.map((a) => a.id);
                this.rental.readerId = this.rental.readerId.map((a) => a.id);
                this.$emit("update:rental", this.rental);
            }
            //clear form fields
            this.rental = {
                bookId: null,
                readerId: null,
                dateOfRental: "",
                dateOfReturn: "",
                returned: false,
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
            this.rental = JSON.parse(JSON.stringify(this.rentalDataSource));
            this.rental.bookId = JSON.parse(
                JSON.stringify(this.rentalDataSource.book)
            );
            this.rental.readerId = JSON.parse(
                JSON.stringify(this.rentalDataSource.reader)
            );
        },
    },
    computed: {
        blankBook() {
            return this.rental.bookId == null;
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

.form-buttons button{
    margin: 10px;
}
.form-header {
    font-size: 20px;
    color: #0d6efd;
}

.returned-checkbox {
    margin-right: 10px;
}



</style>