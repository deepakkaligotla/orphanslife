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
    console.log('Database Connected');
})
const app = express();
app.use(cors())
app.use(express.json());
const saveUserMachineDetails = require('./UserMachine.js')
app.use(saveUserMachineDetails)

const routes = require('./app.js');

app.use('/', routes)

app.listen(4000, () => {
    console.log(`Server Started at ${4000}`)
})