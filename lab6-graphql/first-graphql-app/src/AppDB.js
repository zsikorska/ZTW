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
    todos: () => getDBTodoList(),
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
            return userById(parent.userid);
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
                const {rows} = await client.query('UPDATE users SET name = $1, email = $2, login = $3 WHERE id = $4 RETURNING *',
                 [input.name, input.email, input.login, id]);
                return rows[0];
            } catch (error) {
                throw error
            }
        },
        deleteUser: async ( _, {id}) => {
            try {
                const {rows} = await client.query('DELETE FROM users WHERE id = $1 RETURNING *', [id]);
                return rows[0];
            } catch (error) {
                throw error
            }
        },

        createToDoItem: async (_, {input}) => {
            try {
                const {rows} = await client.query('INSERT INTO todos (title, completed, userid) VALUES ($1, $2, $3) RETURNING *',
                    [input.title, input.completed, input.userId]);
                return rows[0];
            } catch (error) {
                throw error
            }
        },
        updateToDoItem: async ( _, {id, input}) => {
            try {
                const {rows} = await client.query('UPDATE todos SET title = $1, completed = $2, userid = $3 WHERE id = $4 RETURNING *',
                    [input.title, input.completed, input.userId, id]);
                return rows[0];
            } catch (error) {
                throw error
            }
        },
        deleteToDoItem: async ( _, {id}) => {
            try {
                const {rows} = await client.query('DELETE FROM todos WHERE id = $1 RETURNING *', [id]);
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
        const {rows} = await client.query('SELECT * FROM todos WHERE userid = $1', [id]);
        return rows;
    } catch (error) {
        throw error
    }
}

async function todoById(id) {
    try {
        const {rows} = await client.query('SELECT * FROM todos WHERE id = $1', [id]);
        return rows[0];
    } catch (error) {
        throw error
    }
}

async function userById(id) {
    try {
        const {rows} = await client.query('SELECT * FROM users WHERE id = $1', [id]);
        return rows[0];
    } catch (error) {
        throw error
    }
}
async function getDBUsersList() {
    try {
        const {rows} = await client.query('SELECT * FROM users');
        return rows;
    } catch (error) {
        throw error
    }
}

async function getDBTodoList() {
    try {
        const {rows} = await client.query('SELECT * FROM todos');
        return rows;
    } catch (error) {
        throw error
    }
}
