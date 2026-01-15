const multer = require("multer")
const path = require("path")

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        let folder = "uploads"
        
        if(file.fieldname === "image" && req.baseUrl.includes("event")){
            folder = "uploads/event"
        }
        if(file.fieldname === "image" && req.baseUrl.includes("method")){
            folder = "uploads/payment"
        }

        cb(null, folder)
    },
    filename: (req, file, cb) => {
        cb(null, file.originalname)
    }
})

const uploadEvent = multer({
    storage
})

const uploadPayment = multer({
    storage
})

module.exports = { uploadEvent, uploadPayment }