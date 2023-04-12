const express = require('express')
const db = require('./db')
const utils = require('./utils')

const router = express.Router()

router.get('/adoptstatus', (request, response) => {
    const statement = `SELECT * FROM adoptive_status`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.post('/newadoptstatus', (request, response) => {
    const {status} = request.body
    const statement = `insert into adoptive_status(status) VALUES(?)`
    db.pool.query(statement, [status], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.delete('/deleteAdoptiveStatusById/:id', (request, response) => {
    console.log(request.params.id);
    const statement = `Delete from adoptive_status where adoptive_status_id="${request.params.id}"`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdAdoptStatus/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select status from adoptive_status where adoptive_status_id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router