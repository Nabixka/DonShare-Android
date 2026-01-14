const pool = require("../database/db")

const getEvent = async () => {
    const result = await pool.query(`
        SELECT * FROM event`)

    return result.rows
}

const getEventById = async (id) => {
    const result = await pool.query(`
        SELECT * FROM event WHERE id = $1`,
    [id])

    return result.rows[0]
}

const createEvent = async (data) => {
    const { name, image } = data
    const create = await pool.query(`
        INSERT INTO event (name, image) VALUES ($1, $2) RETURNING id`,
    [name, image])

    const newId = create.rows[0].id
    const result = await pool.query(`
        SELECT * FROM event WHERE id = $1`,
    [newId])

    return result.rows[0]
}

module.exports = { getEvent, getEventById, createEvent}