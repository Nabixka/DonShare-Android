const express = require("express")
const port = 3000
const app = express()
const users = require("./routers/userRouter")

app.use(express.json())
app.use("/users", users)

app.listen( port, () => {
    console.log("Server Berjalan Di: http://localhost:3000")
})