<template>
    <div id="app" class="small-container">
        <h1>Readers</h1>
        <reader-form :readerDataSource="readerData" @add:reader="addReader" @update:reader="updateReader"/>
        <readers-table :readerSource="readers" @delete:reader="deleteReader" @update:reader="sendReaderData" />
    </div>
</template>

<script>
import ReadersTable from "@/components/readers/ReadersTable.vue";
import ReaderForm from "@/components/readers/ReaderForm.vue";
import LayoutDefault from "@/layouts/LayoutDefault.vue";

export default {

    name: 'Readers',
    created() {
        this.$emit('update:layout', LayoutDefault);
    },

    components: {
        ReadersTable,
        ReaderForm,
    },
    data() {
        return {
            readers: [],
            readerData: {
                firstName: "",
                lastName: "",
                email: ""
            }
        };
    },
    methods: {
        async getReaders() {
            try {
                const response = await fetch("http://localhost:8080/readers");
                const data = await response.json();
                this.readers = data;
            } catch (error) {
                console.error(error);
            }
        },
        async addReader(reader) {
            const requestOptions = {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(reader),
            };
            const response = await fetch(
                "http://localhost:8080/readers",
                requestOptions
            );
            const data = await response.json();
            this.readers = [...this.readers, data];
        },
        async updateReader(reader) {
            const requestOptions = {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(reader),
            };
            const response = await fetch(
                "http://localhost:8080/readers/" + reader.id,
                requestOptions
            );
            console.log(response);
            this.getReaders()
        },
        async deleteReader(index) {
            console.log(index)
            const response = await fetch("http://localhost:8080/readers/" + index, {
                method: "DELETE",
            });
            console.log(response);
            this.getReaders()
        },
        sendReaderData(reader) {
            console.log(reader)
            this.readerData = reader
        },
    },
    mounted() {
        this.getReaders();
    },
};
</script>
<style>
button {
    background: #009435;
    border: 1px solid #009435;
}
.small-container {
    max-width: 680px;
}
</style>
