const { Pool } = require("pg")

const pool = new Pool ({
    user : "postgres",
    host : "localhost",
    password : "Your_password",
    database : "donshare",
    port : 5432
})

module.exports = pool