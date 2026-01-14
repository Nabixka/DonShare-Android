const user = require("../models/userModel")
const bcrypt = require("bcrypt")

exports.getUsers = async (req, res) => {
    try {
        const list = await user.getUsers()

        res.status(200).json({
            status: 200,
            message: "Berhasil Mengambil Users",
            data: list
        })
    }
    catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.getUser = async (req, res) => {
    try {
        const { id } = req.params
        const list = await user.getUser(id)

        if (!list) {
            res.status(404).json({
                status: 404,
                message: "User Tidak Ada"
            })
        }

        res.status(200).json({
            status: 200,
            message: "Berhasil Mengambil Users",
            data: list
        })
    }
    catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.deleteUser = async (req, res) => {
    try {
        const { id } = req.params

        const exist = await user.getUser(id)
        if (!exist) {
            return res.status(404).json({
                status: 404,
                message: "Tidak Ada User"
            })
        }

        const list = await user.deleteUser(id)
        res.status(200).json({
            status: 200,
            message: "Berhasil Menghapus User"
        })
    }
    catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.createUser = async (req, res) => {
    try {
        const { name, email, password } = req.body

        if (!name || !email || !password) {
            return res.status(400).json({
                status: 400,
                message: "Isi Yang Benar Wok"
            })
        }
        const list = await user.createUser({ name, email, password })
        res.status(201).json({
            status: 201,
            message: "Berhasil Registrasi",
            data: list
        })
    }
    catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.updateUser = async (req, res) => {
    try {
        const { id } = req.params
        const { name } = req.body

        const exist = await user.getUser(id)
        if (!exist) {
            return res.status(404).json({
                stauts: 404,
                message: "User Tidak Ada"
            })
        }
        if (!name) {
            return res.status(400).json({
                status: 400,
                message: "Isi Yang Benar WOk"
            })
        }

        const list = await user.updateUser(id, name)
        res.status(200).json({
            status: 200,
            message: "Berhasil Mengubah Detail Users",
            data: {
                id: data.id,
                name: data.name,
                email: data.email
            }
        })
    }
    catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}

exports.loginUser = async (req, res) => {
    try {
        const { email, password} = req.body

        if(!email || !password){
            return res.status(400).json({
                status: 400,
                message: "Isi Yang Benar Wok"
            })
        }

        const data = await user.getEmailUser(email)
        if(!data){
            return res.status(404).json({
                status: 404,
                message: "Tidak Ada User"
            })
        }

        const match = await bcrypt.compare(password, data.password)
        if(!match){
            return res.status(401).json({
                status: 401,
                message: "Password Salah"
            })
        }

        res.status(200).json({
            status: 200,
            message: "Berhasil Login",
            data: {
                "id" : data.id,
                "name" : data.name,
                "email" : data.email
            }
        })
    }
    catch (err) {
        res.status(500).json({
            status: 500,
            message: err.message
        })
    }
}