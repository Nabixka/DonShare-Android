const express = require("express")
const port = 3000
const app = express()
const users = require("./routers/userRouter")
const event = require("./routers/eventRouter")
const donasi = require("./routers/donasiRouter")
const payment = require("./routers/paymentRouter")

app.use(express.json())
app.use("/users", users)
app.use("/event", event)
app.use("/donasi", donasi)
app.use("/payment", payment)
app.use("/uploads", express.static("uploads"))

app.listen( port, () => {
    console.log("Server Berjalan Di: http://localhost:3000")
})