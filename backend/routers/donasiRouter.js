const donasiController = require("../controllers/donasiController")
const express = require("express")
const router = express.Router()

router.get("/", donasiController.getDonasi)
router.get("/user/:user_id", donasiController.getDonasiByUser)
router.get("/:id", donasiController.getDonasiById)
router.post("/", donasiController.createDonasi)

module.exports = router