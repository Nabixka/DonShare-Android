const pool = require("./db")

async function createTable() {
    try {
        await pool.query(`
        CREATE TABLE IF NOT EXISTS users(
        id SERIAL PRIMARY KEY,
        name VARCHAR,
        password VARCHAR,
        email VARCHAR UNIQUE
        )
    `)

        await pool.query(`
        CREATE TABLE IF NOT EXISTS event(
        id SERIAL PRIMARY KEY,
        name VARCHAR,
        image TEXT
        )
    `)

        await pool.query(`
        CREATE TABLE IF NOT EXISTS currency(
        id SERIAL PRIMARY KEY,
        user_id INT UNIQUE,
        balance BIGINT DEFAULT 0,

        FOREIGN KEY (user_id) REFERENCES users(id)
        )
    `)

        await pool.query(`
        CREATE TABLE IF NOT EXISTS donasi(
        id SERIAL PRIMARY KEY,
        user_id INT,
        event_id INT,
        amount BIGINT,

        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (event_id) REFERENCES event(id)
        )
    `)

        await pool.query(`
        CREATE TABLE IF NOT EXISTS history(
        id SERIAL PRIMARY KEY,
        user_id INT,
        donasi_id INT,
        
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (donasi_id) REFERENCES donasi(id)
        )
    `)

        console.log("Berhasil Membuat Table")
        
    }
    catch (err) {
        console.log("Error: ", err.message)
    }
    finally {
        process.exit(0)
    }
}

createTable()