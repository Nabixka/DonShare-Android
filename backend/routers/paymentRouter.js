const paymentController = require("../controllers/paymentController")
const express = require("express")
const router = express.Router()

router.get("/method/", paymentController.getPaymentMethod)
router.get("/method/:id", paymentController.getPaymentMethodById)
router.get("/user/", paymentController.getUserPayment)
router.get("/user/:id", paymentController.getUserPaymentById)
router.post("/user/", paymentController.createUserPayment)


module.exports = router