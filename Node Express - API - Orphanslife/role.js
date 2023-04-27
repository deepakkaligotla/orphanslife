const express = require('express')
const db = require('./db')
const utils = require('./utils')
const router = express.Router()
const auth = require('./Auth/auth.js')
const { admin, editor, viewer } = require("./Auth/roles.js");

router.get('/roles', [auth], (request, response) => {
    const statement = `SELECT * FROM role`
    db.pool.query(statement, (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

router.get('/findByIdRole/:id', [auth], (request, response) => {
    const id = request.params.id
    console.log(id);
    const statement = `select role from role where id=${request.params.id};`
    db.pool.query(statement, [id], (error, result) => {
        response.send(utils.createResult(error, result))
    })
})

module.exports = router