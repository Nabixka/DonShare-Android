const event = require("../models/eventModel")

exports.getEvent = async (req, res) => {
    try{
        const list = await event.getEvent()
        res.status(200).json({
            status: 200,
            message: "Berhasil Mendapatkan Semua Event",
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

exports.getEventById = async (req, res) => {
    try{
        const { id } = req.params
        const list = await event.getEventById(id)
        if(!list){
            return res.status(404).json({
                status: 404,
                message: "Tidak Ada Open Donasi"
            })
        }

        res.status(200).json({
            status: 200,
            message: "Berhasil Mendapatkan Semua Event",
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

exports.createEvent = async (req, res) => {
    try{
        const { name } = req.body
        
        const imageUrl = req.file ? `http://192.168.56.1:3000/uploads/event/${req.file.filename}` : null

        if(!name || !imageUrl){
            return res.status(400).json({
                status: 404,
                message: "Isi yang Benar Wok"
            })
        }

        const list = await event.createEvent({name, image: imageUrl})

        res.status(201).json({
            status: 201,
            message: "Berhasil Membuat Event Donasi",
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