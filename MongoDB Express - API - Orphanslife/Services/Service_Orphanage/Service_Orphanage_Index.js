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
    console.log('Orphanage Service - MongoDb Connected Successfully');
})
const app = express();
app.use(cors())
app.use(express.json());

const routes = require('./Orphanage_Route.js');

app.use('/', routes)

app.listen(4000, () => {
    console.log(`Orphanage Service Server Started at ${4000}`)
})