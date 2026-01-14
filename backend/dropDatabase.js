const pool = require("./database/db_create")

async function drop(){
    try{
        await pool.query(`
            DROP DATABASE donshare`)

        console.log("Berhasil Menghapus Database")
    }
    catch(err){
        console.log("Error: ", err.message)
    }
    finally{
        process.exit(0)
    }
}

drop()