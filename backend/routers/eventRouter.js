const eventController = require("../controllers/eventController")
const express = require("express")
const router = express.Router()
const { uploadEvent } = require("../multer")

router.get("/", eventController.getEvent)
router.get("/:id", eventController.getEventById)
router.post("/", uploadEvent.single("image"), eventController.createEvent)

module.exports = router