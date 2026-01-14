const multer = require("multer")
const path = require("path")

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        let folder = "uploads"
        
        if(file.fieldname === "image" && req.baseUrl.includes("event")){
            folder = "uploads/event"
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

module.exports = { uploadEvent }