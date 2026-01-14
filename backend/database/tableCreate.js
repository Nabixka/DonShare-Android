const pool = require("./db")

async function createTable() {
    try {
        //done
        await pool.query(`
        CREATE TABLE IF NOT EXISTS users(
        id SERIAL PRIMARY KEY,
        name VARCHAR,
        password VARCHAR,
        email VARCHAR UNIQUE
        )
    `)
        //done
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
        //tinggal currency untuk amount
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