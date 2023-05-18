<template>
    <div id="reader-form">
        <form>
            <div v-if="reader.id != null">
                <p class="form-header" v-text="`Reader id: ${reader.id}`"></p>
            </div>
            <div v-else>
                <p class="form-header">New reader</p>
            </div>
            <label>First name</label>
            <input
                    v-model="reader.firstName"
                    type="text"
                    :class="{ 'has-error': submitting && blankFirstName }"
                    @focus="clearStatus"
                    @keypress="clearStatus"
            />
            <label>Last name</label>
            <input
                    v-model="reader.lastName"
                    type="text"
                    :class="{ 'has-error': submitting && blankLastName }"
                    @focus="clearStatus"
            />
            <label>Email</label>
            <input
                    v-model="reader.email"
                    type="email"
                    :class="{ 'has-error': submitting && (blankEmail || !validEmail) }"
                    @focus="clearStatus"
            />
            <p v-if="error && submitting" class="error-message">
                Fill out requested fields
            </p>
            <p v-else-if="wrongEmail && submitting" class="error-message">
                Wrong email format
            </p>
            <p v-if="success" class="success-message">
                Data has been saved successfully
            </p>
            <b-button variant="primary" @click="handleSubmit">Save</b-button>
            <b-button variant="outline-primary" v-if="reader.id != null" @click="reset">Reset</b-button>
        </form>
    </div>
</template>
<script>
export default {
    name: "reader-form",
    props: {
        readerDataSource: Object,
    },
    data() {
        return {
            submitting: false,
            error: false,
            wrongEmail: false,
            success: false,
            reader: JSON.parse(JSON.stringify(this.readerDataSource)),
        };
    },

    watch: {
        readerDataSource() {
            this.reader = JSON.parse(JSON.stringify(this.readerDataSource));
        },
    },

    methods: {
        handleSubmit() {
            this.submitting = true;
            this.clearStatus();
            //check form fields
            if (this.blankFirstName || this.blankLastName || this.blankEmail) {
                this.error = true;
                return;
            }
            else if (!this.validEmail) {
                this.wrongEmail = true;
                return;
            }
            if (this.reader.id == null) {
                this.$emit("add:reader", this.reader);
                console.log("adding");
            } else {
                this.$emit("update:reader", this.reader);
                console.log("updating");
            }
            //clear form fields
            this.reader = {
                firstName: "",
                lastName: "",
                email: "",
            };
            this.error = false;
            this.wrongEmail = false;
            this.success = true;
            this.submitting = false;
        },
        clearStatus() {
            this.success = false;
            this.wrongEmail = false;
            this.error = false;
        },
        reset() {
            this.reader = JSON.parse(JSON.stringify(this.readerDataSource));
            // this.reader = this.getReader(this.reader.id)
            console.log(this.readerDataSource);
        },
    },
    computed: {
        blankFirstName() {
            return (
                this.reader.firstName === ""
            );
        },
        blankLastName() {
            return (
                this.reader.lastName.trim() === ""
            );
        },
        blankEmail() {
            return (
                this.reader.email.trim() === ""
            );
        },
        validEmail() {
            return (
                this.reader.email.includes("@")
            );
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
</style>