const { GraphQLServer } = require('graphql-yoga');
const axios = require("axios");
const { Client } = require('pg');

const client = new Client({
    host: 'localhost',
    user: 'postgres',
    database: 'todo',
    password: 'root',
    port: 5432,
  });
client.connect()

const resolvers = {
    Query: {
    users: async () => getDBUsersList(),
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

    Mutation: {
        createUser: async (_, {input}) => {
            try {
                const {rows} = await client.query('INSERT INTO users (name, email, login) VALUES ($1, $2, $3) RETURNING *',
                 [input.name, input.email, input.login]);
                return rows[0];
            } catch (error) {
                throw error
            }
        },
        updateUser: async ( _, {id, input}) => {
            try {
                console.log(input)
                const {rows} = await client.query('UPDATE users SET name = $1, email = $2, login = $3 WHERE id = $4 RETURNING *',
                 [input.name, input.email, input.login, id]);
                 console.log(rows)
                return rows[0];
            } catch (error) {
                throw error
            }
        }
    }
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
async function getDBUsersList() {
    try {
        try {
            const {rows} = await client.query('SELECT * FROM users');
            return rows;
        } catch (error) {
            console.log(error)
        }
    } catch (error) {
        throw error
    }
}

async function createUser() {
    try {
        try {
            const {rows} = await client.query('SELECT * FROM users');
            return rows;
        } catch (error) {
            console.log(error)
        }
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
