const pool = require("../database/db")

const getDonasi = async () => {
    const result = await pool.query(`
        SELECT 
        d.id,
        json_build_object(
            'id', u.id,
            'name', u.name
        ) AS user,
        json_build_object(
            'id', e.id,
            'name', e.name
        ) AS event,
        d.amount
        
        FROM donasi d
        LEFT JOIN users u ON d.user_id = u.id
        LEFT JOIN event e ON d.event_id = e.id

        `)

    return result.rows
}

const getDonasiById = async (id) => {
    const result = await pool.query(`
        SELECT 
        d.id,
        json_build_object(
            'id', u.id,
            'name', u.name
        ) AS user,
        json_build_object(
            'id', e.id,
            'name', e.name
        ) AS event,
        d.amount
        
        FROM donasi d 
        LEFT JOIN users u ON d.user_id = u.id
        LEFT JOIN event e ON d.event_id = e.id
        WHERE d.id = $1
        `,
        [id])

    return result.rows[0]
}

const createDonasi = async (data) => {
    const { user_id, event_id, amount } = data

    const create = await pool.query(
        `INSERT INTO donasi (user_id, event_id, amount)
         VALUES ($1, $2, $3)
         RETURNING id`,
        [user_id, event_id, amount]
    )

    const update = await pool.query(`
        UPDATE event SET total_amount = total_amount + $1 WHERE id = $2`,
    [amount, event_id])

    const newId = create.rows[0].id

    const result = await pool.query(`
        SELECT 
            d.id,
            json_build_object(
                'id', u.id,
                'name', u.name
            ) AS user,
            json_build_object(
                'id', e.id,
                'name', e.name
            ) AS event,
            d.amount
        FROM donasi d 
        LEFT JOIN users u ON d.user_id = u.id
        LEFT JOIN event e ON d.event_id = e.id
        WHERE d.id = $1
    `, [newId])

    return result.rows[0]
}

const getDonasiByUser = async (id) => {
    const result = await pool.query(`
        SELECT 
            u.name as user_name,
            d.id as donation_id,
            e.name as event_name,
            e.image as event_image,
            d.amount
        FROM donasi d
        JOIN users u ON d.user_id = u.id
        JOIN event e ON d.event_id = e.id
        WHERE d.user_id = $1
        ORDER BY d.id DESC
    `, [id]);

    if (result.rows.length === 0) return null;

    const formattedData = {
        user: {
            name: result.rows[0].user_name
        },
        donations: result.rows.map(row => ({
            id: row.donation_id,
            event: { 
                name: row.event_name,
                image: row.event_image
            },
            amount: row.amount
        }))
    };

    return formattedData;
}

module.exports = { getDonasi, getDonasiById, createDonasi, getDonasiByUser }