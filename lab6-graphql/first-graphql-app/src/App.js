const { GraphQLServer } = require('graphql-yoga');
const axios = require("axios")

const resolvers = {
    Query: {
    users: async () => getRestUsersList(),
    todos: () => getRestTodoList(),
    todo: (parent, args, context, info) => todoById(args.id),
    user: (parent, args, context, info) => userById(args.id),
    },
    User: {
        todos: (parent, args, context, info) => {
            return todosByUserId(parent.id);
        }
    },
    ToDoItem: {
        user: (parent, args, context, info) => {
            return userById(parent.userId);
        }
    },
}

const server = new GraphQLServer({
    typeDefs: './src/schema.graphql',
    resolvers,
});

server.start(() => console.log(`Server is running on http://localhost:4000`));

async function todosByUserId(id) {
    try {
        const todos = await axios.get(`https://jsonplaceholder.typicode.com/todos?userId=${id}`)
        return todos.data
    } catch (error) {
        throw error
    }
}

async function todoById(id) {
    try {
        const user = await axios.get(`https://jsonplaceholder.typicode.com/todos/${id}`)
        return user.data
    } catch (error) {
        throw error
    }
}

async function userById(id) {
    try {
        const user = await axios.get(`https://jsonplaceholder.typicode.com/users/${id}`)
        console.log('user by id')
        return user.data
    } catch (error) {
        throw error
    }
}
async function getRestUsersList() {
    try {
        const users = await axios.get("https://jsonplaceholder.typicode.com/users")
        console.log(users);
        return users.data.map(({ id, name, email, username }) => ({
            id: id,
            name: name,
            email: email,
            login: username,
        }))
    } catch (error) {
        throw error
    }
}

async function getRestTodoList() {
    try {
        const todos = await axios.get("https://jsonplaceholder.typicode.com/todos")
        return todos.data
    } catch (error) {
        throw error
    }
}
