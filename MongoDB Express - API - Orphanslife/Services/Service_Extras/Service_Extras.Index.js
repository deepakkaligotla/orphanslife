const express = require('express')
require('dotenv').config()
const cors = require('cors')
const mongoose = require('mongoose')
const mongoString = process.env.ATLAS_URI
mongoose.connect(mongoString)
const database = mongoose.connection
const extras_route = require('./Extras_Route.js')

database.on('error', (error) => {
    console.log(error)
})

database.once('connected', () => {
    console.log('Extras Service - MongoDb Connected Successfully')
})

const app = express()
app.use(cors())
app.use(express.json())

app.use('/', extras_route)

app.listen(3000, () => {
    console.log(`Extras Service Server Started at ${3000}`)
})