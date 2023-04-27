const express = require('express')
const db = require('./db')
const utils = require('./utils')
const router = express.Router()
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");

router.get('/adoptstatus', [auth], (request, response) => {
    const statement = `SELECT * FROM adoptive_status`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newadoptstatus', [auth, editor], (request, response) => {
    const {status} = request.body
    const statement = `insert into adoptive_status(status) VALUES(?)`
    db.pool.query(statement, [status], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deleteAdoptiveStatusById/:id', [auth, admin], (request, response) => {
    console.log(request.params.id);
    const statement = `Delete from adoptive_status where adoptive_status_id="${request.params.id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdAdoptStatus/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select status from adoptive_status where adoptive_status_id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router