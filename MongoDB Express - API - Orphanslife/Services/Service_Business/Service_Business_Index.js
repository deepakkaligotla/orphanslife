const express = require('express')
require('dotenv').config();
const cors = require('cors');
const mongoose = require('mongoose');
const mongoString = process.env.ATLAS_URI;
mongoose.connect(mongoString);
const database = mongoose.connection;

database.on('error', (error) => {
    console.log(error)
})

database.once('connected', () => {
    console.log('Business Service - MongoDb Connected Successfully');
})
const app = express();
app.use(cors())
app.use(express.json());

const routes = require('./Business_Route.js');

app.use('/', routes)

app.listen(2000, () => {
    console.log(`Business Service Server Started at ${2000}`)
})