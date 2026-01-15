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
        image TEXT,
        total_amount BIGINT DEFAULT 0
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
        CREATE TABLE IF NOT EXISTS payment_method(
        id SERIAL PRIMARY KEY,
        name VARCHAR UNIQUE,
        image TEXT
        )        
    `)

        await pool.query(`
        CREATE TABLE IF NOT EXISTS user_payment(
        id SERIAL PRIMARY KEY,
        user_id INT,
        payment_method_id INT,
        nomor VARCHAR,

        UNIQUE (user_id, payment_method_id),
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (payment_method_id) REFERENCES payment_method(id)
        )
    `)

        await pool.query(`
        CREATE TABLE IF NOT EXISTS donasi(
        id SERIAL PRIMARY KEY,
        user_id INT,
        event_id INT,
        user_payment_id INT,
        amount BIGINT,

        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (event_id) REFERENCES event(id),
        FOREIGN KEY (user_payment_id) REFERENCES user_payment(id)
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