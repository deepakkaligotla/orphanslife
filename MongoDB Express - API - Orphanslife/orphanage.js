const express = require('express')
const db = require('./db')
const utils = require('./utils')
const multer = require('multer')
const cryptoJs = require('crypto-js')
const mailer = require('./mailer')
const upload = multer()
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");
const router = express.Router()

router.get('/orphanages', [auth], (request, response) => {
    const statement = `SELECT * FROM orphanage`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdOrphanage/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select orphanage.*, location.pincode, location.area, location.city, location.district, location.state FROM orphanage LEFT JOIN location ON orphanage.location_id = location.id where orphanage.id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router