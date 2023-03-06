const express = require('express')
const db = require('./db')
const utils = require('./utils')

const router = express.Router()

router.get('/roles', (request, response) => {
    const statement = `SELECT * FROM role`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdRole/:id', (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select role from role where id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router