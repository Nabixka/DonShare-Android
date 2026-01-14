const pool = require("./db_create")

async function createDB(){
    try{
        await pool.query(`
            CREATE DATABASE donshare
        `)

        console.log("Berhasil Membuat Database")
    }
    catch(err){
        console.log("Error: ", err.message)
    }
    finally{
        process.exit(0)
    }
}

createDB()