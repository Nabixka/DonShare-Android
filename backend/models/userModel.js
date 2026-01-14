const pool = require("../database/db")
const bcrypt = require("bcrypt")

const getUsers = async () => {
    const result = await pool.query(`
        SELECT * FROM users`)
    
    return result.rows
}

const getUser = async (id) => {
    const result = await pool.query(`
        SELECT * FROM users WHERE id = $1`,
    [id])
    
    return result.rows[0]
}

const deleteUser = async (id) => {
    const result = await pool.query(`
        DELETE FROM users WHERE id = $1`,
    [id])
}

const createUser = async (data) => {
    const { name, email, password} = data
    const hashed = await bcrypt.hash(password, 10)

    const post = await pool.query(`
        INSERT INTO users (name, email, password) VALUES ($1, $2, $3) RETURNING id`,
    [name, email, hashed])

    const newId = post.rows[0].id
    const result = await pool.query(`
        SELECT * FROM users WHERE id = $1`,
    [newId])

    const user =  result.rows[0]
    delete user.password
    return user
}

const updateUser = async (id, data) => {
    const { name } = data

    const put = await pool.query(`
        UPDATE SET name = $1 WHERE id = $2 RETURNING id`,
    [name, id])
    
    const newId = put.rows[0].id
    const result = await pool.query(`
        SELECT * FROM users WHERE id = $1`
    [newId])

    return result.rows[0]
}

const getEmailUser = async (email) => {
    const result  = await pool.query(`
        SELECT * FROM users WHERE email = $1`,
    [email])

    return result.rows[0]

}

module.exports = { getUsers, getUser, deleteUser, createUser, updateUser, getEmailUser }