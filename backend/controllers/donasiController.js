const donasi = require("../models/donasiModel")
const event = require("../models/eventModel")
const user = require("../models/userModel")

exports.getDonasi = async (req, res) => {
    try{
        const list = await donasi.getDonasi()
        res.status(200).json({
            status: 200,
            message: "Berhasil",
            data: list
        })
    }
    catch(err){
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.getDonasiById = async (req, res) => {
    try{
        const { id } = req.params
        const list = await donasi.getDonasiById(id)
        if(!list){
            return res.status(200).json({
                status: 200,
                message: "Tidak Ada Donasi"
            })
        }

        res.status(200).json({
            status: 200,
            message: "Berhasil",
            data: list
        })
    }
    catch(err){
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.createDonasi = async (req, res) => {
    try{
        const { user_id, event_id, amount } = req.body

        if(!user_id || !event_id || !amount){
            return res.status(400).json({
                status: 400,
                message: "Isi Yang Benar Wok"
            })
        }

        const user_exist = await user.getUser(user_id)
        if(!user_exist){
            return res.status(404).json({
                status: 404,
                message: "Tidak Ada User"
            })
        }

        const event_exist = await event.getEventById(event_id)
        if (!event_exist) {
            return res.status(404).json({
                status: 404,
                message: "Event tidak ditemukan"
            })
        }

        const list = await donasi.createDonasi({user_id, event_id, amount})
        res.status(201).json({
            status: 201,
            message: "Berhasil Berdonasi",
            data: list
        })

    }
    catch(err){
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.getDonasiByUser = async (req, res) => {
    try{
        const { user_id } = req.params

        const list = await donasi.getDonasiByUser(user_id)
        res.status(200).json({
            status: 200,
            message: "Berhasil",
            data: list
        })
    }
    catch(err){
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }   
}