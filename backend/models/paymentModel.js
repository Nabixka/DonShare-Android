const pool = require("../database/db")

//payment method
const getPaymentMethod = async () => {
    const result = await pool.query(`
        SELECT * FROM payment_method`
    )

    return result.rows
}

const getPaymentMethodById = async (id) => {
    const result = await pool.query(`
        SELECT * FROM payment_method WHERE id = $1`,
    [id])

    return result.rows[0]
}

//user payment
const getUserPayment = async () => {
    const result = await pool.query(`
        SELECT
        up.id,
        json_build_object(
        'id', u.id,
        'name', u.name
        ) AS user,
        json_build_object(
        'id', pm.id,
        'name', pm.name
        ) AS payment_method,
        up.nomor

        FROM user_payment up
        LEFT JOIN users u ON up.user_id = u.id
        LEFT JOIN payment_method pm ON up.payment_method_id = pm.id`
    )

    return result.rows
}

const getUserPaymentById = async (userId) => {
    const result = await pool.query(`
        SELECT
        up.id,
        json_build_object(
            'id', u.id,
            'name', u.name
        ) AS user,
        json_build_object(
            'id', pm.id,
            'name', pm.name,
            'image', pm.image
        ) AS payment_method,
        up.nomor
        FROM user_payment up
        LEFT JOIN users u ON up.user_id = u.id
        LEFT JOIN payment_method pm ON up.payment_method_id = pm.id
        WHERE up.user_id = $1
    `, [userId])

    return result.rows
}


const createUserPayment = async (data) => {
    const { user_id, payment_method_id, nomor} = data
    const create = await pool.query(`
        INSERT INTO user_payment (user_id, payment_method_id, nomor) VALUES ($1, $2, $3) RETURNING id`,
    [ user_id, payment_method_id, nomor])

    const newId = create.rows[0].id
    const result = await pool.query(`
        SELECT
        up.id,
        json_build_object(
        'id', u.id,
        'name', u.name
        ) AS user,
        json_build_object(
        'id', pm.id,
        'name', pm.name
        ) AS payment_method,
        up.nomor

        FROM user_payment up
        LEFT JOIN users u ON up.user_id = u.id
        LEFT JOIN payment_method pm ON up.payment_method_id = pm.id

        WHERE up.id = $1   
    `, [newId])

    return result.rows[0]
}

module.exports = { 
    getPaymentMethod, 
    getPaymentMethodById,
    getUserPayment,
    getUserPaymentById,
    createUserPayment
}