const { Pool, Client } = require('pg')
const axios = require("axios");

const client = new Client({
    host: 'localhost',
    user: 'postgres',
    database: 'todo',
    password: 'root',
    port: 5432,
  });

const execute = async (query, values) => {
    try {
        await client.connect();     // gets connection
        await client.query(query, values);  // sends queries
        return true;
    } catch (error) {
        console.error(error.stack);
        return false;
    }
};


async function insertUsersData() {
    try {
        const resp = await axios.get(`https://jsonplaceholder.typicode.com/users`);

        try {
            const insertUsersQuery = 'INSERT INTO users (name, email, login) VALUES ($1, $2, $3) ON CONFLICT DO NOTHING';
             
            for (const userData of resp.data) {
                await client.query(insertUsersQuery, [userData.name, userData.email, userData.username])
                    .catch(err => console.error(err));
            }
        } catch (error) {
            throw error;
        }
    } catch (err) {
        console.error(err);
    }
}

async function insertTodosData() {
    try {
        const resp = await axios.get(`https://jsonplaceholder.typicode.com/todos`);

        try {
            const insertUsersQuery = 'INSERT INTO todos (title, completed, userId) VALUES ($1, $2, $3) ON CONFLICT DO NOTHING';
              
            for (const todoData of resp.data) {
                await client.query(insertUsersQuery, [todoData.title, todoData.completed, todoData.userId, ])
                    .catch(err => console.error(err));
            }
        } catch (error) {
            throw error;
        } finally {
            await client.end();   
        }
    } catch (err) {
        console.error(err);
    }
}

const createTablesQuery = `
    CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    login TEXT NOT NULL UNIQUE);
    CREATE TABLE IF NOT EXISTS todos (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT false,
    userId INTEGER NOT NULL REFERENCES users(id));`;

execute(createTablesQuery).then(result => {
    if (result) {
        console.log('Tables created');
    }
});

insertUsersData();
insertTodosData();
