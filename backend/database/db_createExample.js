const { Pool } = require("pg")

const pool = new Pool ({
    user : "postgres",
    host : "localhost",
    password : "Your_Password",
    database : "postgres",
    port : 5432
})

module.exports = pool