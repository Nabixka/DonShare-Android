const donasi = require("../models/donasiModel")
const event = require("../models/eventModel")
const user = require("../models/userModel")

exports.getDonasi = async (req, res) => {
    try {
        const list = await donasi.getDonasi()
        res.status(200).json({
            status: 200,
            message: "Berhasil",
            data: list
        })
    } catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.getDonasiById = async (req, res) => {
    try {
        const { id } = req.params
        const data = await donasi.getDonasiById(id)

        if (!data) {
            return res.status(404).json({
                status: 404,
                message: "Donasi tidak ditemukan"
            })
        }

        res.status(200).json({
            status: 200,
            message: "Berhasil",
            data
        })
    } catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.createDonasi = async (req, res) => {
    try {
        const { user_id, event_id, user_payment_id, amount } = req.body

        if (!user_id || !event_id || !user_payment_id || !amount) {
            return res.status(400).json({
                status: 400,
                message: "Data tidak lengkap"
            })
        }

        const userExist = await user.getUser(user_id)
        if (!userExist) {
            return res.status(404).json({
                status: 404,
                message: "User tidak ditemukan"
            })
        }

        const eventExist = await event.getEventById(event_id)
        if (!eventExist) {
            return res.status(404).json({
                status: 404,
                message: "Event tidak ditemukan"
            })
        }

        const result = await donasi.createDonasi({
            user_id,
            event_id,
            user_payment_id,
            amount
        })

        res.status(201).json({
            status: 201,
            message: "Berhasil berdonasi",
            data: result
        })

    } catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.getDonasiByUser = async (req, res) => {
    try {
        const { user_id } = req.params
        const data = await donasi.getDonasiByUser(user_id)

        if (!data) {
            return res.status(404).json({
                status: 404,
                message: "Belum ada donasi"
            })
        }

        res.status(200).json({
            status: 200,
            message: "Berhasil",
            data
        })
    } catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}
