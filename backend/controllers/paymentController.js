const payment = require("../models/paymentModel")

exports.getPaymentMethod = async (req, res) => {
    try{
        const list = await payment.getPaymentMethod()
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

exports.getPaymentMethodById = async (req, res) => {
    try{
        const list = await payment.getPaymentMethodById()
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

exports.getUserPayment = async (req, res) => {
    try{
        const list = await payment.getUserPayment()
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

exports.getUserPaymentById = async (req, res) => {
    try{
        const { id } = req.params
        
        const list = await payment.getUserPaymentById(id)

        if(!list){
            return res.status(404).json({
                status: 404,
                message: "Tidak Ada"
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

exports.createUserPayment = async (req, res) => {
    try{
        const { user_id, payment_method_id, nomor} = req.body
        
        const list = await payment.createUserPayment({user_id, payment_method_id, nomor})

        res.status(201).json({
            status: 201,
            message: "Created",
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

