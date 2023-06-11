const express = require('express')
require('dotenv').config();
const cors = require('cors');
const mongoose = require('mongoose');
const mongoString = process.env.ATLAS_URI;
mongoose.connect(mongoString);
const database = mongoose.connection;

database.on('error', (error) => {
    console.log("API Service - Unable to Create MongoDB Transaction")
})

database.once('connected', () => {
    console.log('API Service - MongoDB Transaction Created Successfully');
})

const api_auth_app = express();
api_auth_app.use(cors())
api_auth_app.use(express.json());

const api_router = require('./API_Route');

api_auth_app.use('/', api_router)

api_auth_app.listen(80, () => {
    console.log(`API Authentication Server Started at ${80}`)
})