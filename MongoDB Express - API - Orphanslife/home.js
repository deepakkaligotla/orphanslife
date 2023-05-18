const express = require('express')
const db = require('./db')
const utils = require('./utils')
const path = require('path');

const router = express.Router()

router.get('/', (req, res) => {
    res.sendFile(path.join(__dirname,'index.html'));
})

module.exports = router